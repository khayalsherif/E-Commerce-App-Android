package az.red.data.repository.sessionmanager

import az.red.data.local.SessionManager
import az.red.domain.repository.sessionmanager.SessionManagerRepository

class SessionManagerImpl(
    private val sessionManager: SessionManager
) : SessionManagerRepository{

    override fun saveCurrentLanguage(language: String) {
        sessionManager.saveCurrentLanguage(language)
    }

    override fun getCurrentLanguage(): String {
        return sessionManager.getCurrentLanguage()
    }

    override fun saveDarkMode(isDarkMode: Boolean) {
        sessionManager.saveDarkMode(isDarkMode)
    }

    override fun saveAuthToken(token: String,  rememberMe: Boolean) {
        sessionManager.saveAuthToken(token, rememberMe)
    }

    override fun removeAuthToken() {
        sessionManager.removeAuthToken()
    }

    override fun getAuthToken(): String? {
        return sessionManager.getAuthToken()
    }

    override fun getUserId(): String? {
        return sessionManager.getUserId()
    }

    override fun getRememberMe(): Boolean {
        return sessionManager.getRememberMe()
    }

    override fun getCurrentAppTheme(): Boolean {
        return sessionManager.getCurrentAppTheme()
    }
}