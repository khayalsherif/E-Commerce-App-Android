package az.red.data.di

import az.red.data.errors.RemoteErrorMapper
import az.red.data.interceptor.HeaderInterceptor
import az.red.data.local.SessionManager
import az.red.data.remote.auth.AuthService
import az.red.data.repository.auth.AuthRepositoryImpl
import az.red.domain.exception.ErrorMapper
import az.red.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"
const val IO_CONTEXT = "IO_CONTEXT"

val dataModule = module {

    ///////////////////////////////////// REMOTE ///////////////////////////////////////////////

    single {
        val interceptor = HeaderInterceptor(sessionManager = get())

        val builder = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(getProperty("base"))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    // Auth
    factory<AuthService> { get<Retrofit>().create(AuthService::class.java) }

    factory<AuthRepository> {
        AuthRepositoryImpl(service = get())
    }

    ///////////////////////////////////// LOCAL ///////////////////////////////////////////////

    single {
        SessionManager(androidContext())
    }

    ///////////////////////////////////// REMOTE ERROR MAP ///////////////////////////////////////////////

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }
    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }
}