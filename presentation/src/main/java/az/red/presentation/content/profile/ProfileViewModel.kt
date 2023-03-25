package az.red.presentation.content.profile

import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel

class ProfileViewModel(
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    var isDarkMode = false

    init {
        getCurrentAppTheme()
    }

    fun saveDarkMode(isDarkMode: Boolean) {
        sessionManagerUseCase.saveDarkMode(isDarkMode)
        this.isDarkMode = isDarkMode
    }

    private fun getCurrentAppTheme() {
        isDarkMode = sessionManagerUseCase.getCurrentAppTheme()
    }

    fun getCurrentLanguage(): String =
        sessionManagerUseCase.getCurrentLanguage()


    fun saveCurrentLanguage(language: String) {
        sessionManagerUseCase.saveCurrentLanguage(language)
    }

    fun logOut() {
        sessionManagerUseCase.removeAuthToken()
    }
}