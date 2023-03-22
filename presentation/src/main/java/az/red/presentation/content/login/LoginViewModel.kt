package az.red.presentation.content.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.usecase.auth.AuthUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase,
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    init {
        authorizationCheck()
    }

    private fun authorizationCheck() {
        sessionManagerUseCase.getAuthToken().let {
            if (it.isNullOrEmpty()) {
                Log.i("BASE_VIEW_MODEL", "Token is empty. Redirect to login")
//                isLoggedIn.value = false
            } else {
                Log.i("BASE_VIEW_MODEL", "Token is empty. Redirect to login")
//                isLoggedIn.value = true
//                triggerEvent(UIEvent.Navigate(Graph.MAIN))
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            authUseCase.login(LoginRequest("random@gmail.com", "Tesasfasf12t")).collect {
                println(it.data)
            }
        }
    }
}