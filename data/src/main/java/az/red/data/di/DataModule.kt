package az.red.data.di

import az.red.data.remote.interceptor.HeaderInterceptor
import az.red.data.local.SessionManager
import az.red.data.mapper.auth.AuthMapper
import az.red.data.remote.auth.AuthService
import az.red.data.repository.auth.AuthRepositoryImpl
import az.red.domain.repository.auth.AuthRepository
import com.google.gson.Gson
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

const val IO_CONTEXT = "IO_CONTEXT"

val dataModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }

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
        AuthRepositoryImpl(service = get(), mapper = get())
    }

    ///////////////////////////////////// LOCAL ///////////////////////////////////////////////

    single {
        SessionManager(androidContext())
    }

    ///////////////////////////////////// MAPPER ///////////////////////////////////////////////

    single { Gson() }

    factory { AuthMapper() }
}