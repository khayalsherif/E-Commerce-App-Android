package az.red.e_commerce_app_android

import android.app.Application
import az.red.e_commerce_app_android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@App)
            modules(appModule)
        }
    }
}