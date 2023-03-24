package az.red.presentation.content.profile

import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel(
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    val isDarkMode = MutableStateFlow(false)

    init {
        getCurrentAppTheme()
    }

    fun saveDarkMode(isDarkMode:Boolean){
        sessionManagerUseCase.saveDarkMode(isDarkMode)
        this.isDarkMode.value = isDarkMode
    }

    private fun getCurrentAppTheme(){
        isDarkMode.value = sessionManagerUseCase.getCurrentAppTheme()
    }

    fun saveCurrentLanguage(language:String){
        sessionManagerUseCase.saveCurrentLanguage(language)
    }

    fun logOut() {
        sessionManagerUseCase.removeAuthToken()
    }
}