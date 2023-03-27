package az.red.data.common

import az.red.domain.common.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent
import retrofit2.HttpException
import retrofit2.Response

inline fun <T : Any, reified D : Any> handleApi(
    crossinline mapper: (T) -> D,
    crossinline execute: suspend () -> Response<T>
): Flow<NetworkResult<D>> = flow {
    val response = execute()
    val body = response.body()
    emit(NetworkResult.Loading())

    if (response.isSuccessful) {
        if (body == null) emit(NetworkResult.Empty())
        else
            emit(NetworkResult.Success(mapper(body)))
    } else {
        val gson: Gson by KoinJavaComponent.inject(Gson::class.java)
        val e = response.errorBody()?.let { gson.fromJson(it.string(), D::class.java) }
        emit(
            NetworkResult.Error(
                code = response.code(),
                message = response.message(),
                data = e as D
            )
        )
    }
}.catch {
    when (it) {
        is HttpException -> emit(
            NetworkResult.Error(
                message = it.stackTraceToString(),
                code = it.code()
            )
        )
        else -> emit(NetworkResult.Exception(exception = it.stackTraceToString()))
    }
}