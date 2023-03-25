package az.red.presentation.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import java.util.*

class LocalLanguageManager {

    fun onAttach(context: Context, useCase: SessionManagerUseCase): Context {
        val lang = getPersistedData(useCase)
        return setLocale(context, lang, useCase)
    }

    private fun performSetLanguage(context: Context, lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.createConfigurationContext(config)
    }

    private fun setLocale(
        context: Context,
        language: String,
        useCase: SessionManagerUseCase
    ): Context {
        persist( language, useCase)
        try {
            performSetLanguage(context, language)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLegacy(context, language)
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    private fun getPersistedData(useCase: SessionManagerUseCase): String {
        return useCase.getCurrentLanguage()
    }

    private fun persist(
        language: String,
        sessionManagerUseCase: SessionManagerUseCase
    ) {
        sessionManagerUseCase.saveCurrentLanguage(language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val res = context.resources
        // Change locale settings in the app.
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(language))
        res.updateConfiguration(conf, dm)
        return context.createConfigurationContext(conf)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        var locale: Locale? = null
        try {
            locale = Locale(language)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            Locale.setDefault(locale)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}