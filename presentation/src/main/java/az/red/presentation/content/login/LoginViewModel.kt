package az.red.presentation.content.login

import androidx.lifecycle.viewModelScope
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.usecase.auth.AuthUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(private val authUseCase: AuthUseCase) : BaseViewModel() {

    fun login() {
        viewModelScope.launch {
            authUseCase.login(LoginRequest("random@gmail.com", "Tesasfasf12t")).collect {
                println(it.data)
            }
        }
    }
}