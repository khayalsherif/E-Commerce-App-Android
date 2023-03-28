package az.red.data.di

import az.red.data.remote.interceptor.HeaderInterceptor
import az.red.data.local.SessionManager
import az.red.data.mapper.auth.AuthMapper
import az.red.data.mapper.cart.CartMapper
import az.red.data.mapper.order.OrderMapper
import az.red.data.mapper.review.ReviewMapper
import az.red.data.remote.auth.AuthService
import az.red.data.remote.cart.CartService
import az.red.data.remote.category.CategoryService
import az.red.data.remote.order.OrderService
import az.red.data.remote.product.ProductService
import az.red.data.remote.review.ReviewService
import az.red.data.repository.auth.AuthRepositoryImpl
import az.red.data.repository.cart.CartRepositoryImpl
import az.red.data.repository.category.CategoryRepositoryImpl
import az.red.data.repository.order.OrderRepositoryImpl
import az.red.data.repository.product.ProductRepositoryImpl
import az.red.data.repository.review.ReviewRepositoryImpl
import az.red.data.repository.sessionmanager.SessionManagerImpl
import az.red.domain.repository.auth.AuthRepository
import az.red.domain.repository.cart.CartRepository
import az.red.domain.repository.category.CategoryRepository
import az.red.domain.repository.order.OrderRepository
import az.red.domain.repository.product.ProductRepository
import az.red.domain.repository.review.ReviewRepository
import az.red.domain.repository.sessionmanager.SessionManagerRepository
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


    factory<CartService> { get<Retrofit>().create(CartService::class.java) }

    factory<AuthRepository> {
        AuthRepositoryImpl(service = get(), mapper = get())
    }
    factory<CartRepository> {
        CartRepositoryImpl(cartService = get(), cartMapper = get())
    }


    // Category
    factory<CategoryService> { get<Retrofit>().create(CategoryService::class.java) }

    factory<CategoryRepository> {
        CategoryRepositoryImpl(service = get())
    }

    //Order
    factory<OrderService> { get<Retrofit>().create(OrderService::class.java) }

    factory<OrderRepository> {
        OrderRepositoryImpl(orderService = get(), orderMapper = get())
    }

    // Product
    factory<ProductService> { get<Retrofit>().create(ProductService::class.java) }

    factory<ProductRepository> {
        ProductRepositoryImpl(service = get())
    }

    //Review
    factory<ReviewService> { get<Retrofit>().create(ReviewService::class.java) }

    factory<ReviewRepository> {
        ReviewRepositoryImpl(reviewService = get(), reviewMapper = get())
    }

    // Session Manager
    factory<SessionManagerRepository> {
        SessionManagerImpl(sessionManager = get())
    }

    ///////////////////////////////////// LOCAL ///////////////////////////////////////////////

    single {
        SessionManager(androidContext())
    }

    ///////////////////////////////////// MAPPER ///////////////////////////////////////////////

    single { Gson() }

    factory { AuthMapper() }
    factory { CartMapper() }
    factory { OrderMapper() }
    factory { ReviewMapper() }
}