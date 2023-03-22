package az.red.domain.usecase.sessionmanager

import az.red.domain.repository.sessionmanager.SessionManagerRepository

class SessionManagerUseCase(
    private val sessionManagerRepository: SessionManagerRepository
) {

    fun saveCurrentLanguage(language: String){
        sessionManagerRepository.saveCurrentLanguage(language)
    }
    fun getCurrentLanguage():String {
        return sessionManagerRepository.getCurrentLanguage()
    }
    fun saveDarkMode(isDarkMode: Boolean) {
        sessionManagerRepository.saveDarkMode(isDarkMode)
    }
    fun saveAuthToken(token: String, userId: String, rememberMe: Boolean) {
        sessionManagerRepository.saveAuthToken(token, userId, rememberMe)
    }
    fun removeAuthToken() {
        sessionManagerRepository.removeAuthToken()
    }
    fun getAuthToken(): String? {
        return sessionManagerRepository.getAuthToken()
    }
    fun getUserId(): String? {
        return sessionManagerRepository.getUserId()
    }
    fun getRememberMe(): Boolean {
        return sessionManagerRepository.getRememberMe()
    }

}