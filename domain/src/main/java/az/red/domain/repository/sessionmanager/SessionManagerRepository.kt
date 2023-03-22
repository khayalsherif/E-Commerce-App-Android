package az.red.domain.repository.sessionmanager

interface SessionManagerRepository {
    fun saveCurrentLanguage(language: String)
    fun getCurrentLanguage():String
    fun saveDarkMode(isDarkMode: Boolean)
    fun saveAuthToken(token: String, userId: String, rememberMe: Boolean)
    fun removeAuthToken()
    fun getAuthToken(): String?
    fun getUserId(): String?
    fun getRememberMe(): Boolean
}