package az.red.data.common

import az.red.domain.common.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T : Any> handleApi(
    crossinline execute: suspend () -> Response<T>
): Flow<NetworkResult<T>> = flow {
    val response = execute()
    val body = response.body()
    emit(NetworkResult.Loading())

    if (response.isSuccessful && body != null) {
        emit(NetworkResult.Success(body))
    } else {
        val gson: Gson by KoinJavaComponent.inject(Gson::class.java)
        val e = response.errorBody()?.let { gson.fromJson(it.string(), T::class.java) }
        emit(
            NetworkResult.Error(
                code = response.code(),
                message = response.message(),
                data = e as T
            )
        )
    }
}.catch {
    when (it) {
        is HttpException -> emit(NetworkResult.Error(message = it.message(), code = it.code()))
        else -> emit(NetworkResult.Exception(exception = it.message!!))
    }
}