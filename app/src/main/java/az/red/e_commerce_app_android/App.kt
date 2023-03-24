package az.red.e_commerce_app_android

import android.app.Application
import android.content.Context
import az.red.e_commerce_app_android.di.appModule
import az.red.presentation.util.LocalLanguageManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocalLanguageManager().onAttach(base!!))
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            properties(
                mapOf("base" to "https://mobile.test-danit.com/api/")
            )
            androidContext(androidContext = this@App)
            modules(appModule)
        }
    }
}