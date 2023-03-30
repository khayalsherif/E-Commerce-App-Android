package az.red.presentation.content.login

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.usecase.auth.LoginUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    val isLoggedIn = MutableStateFlow(false)
    private val _loginResponse =
        MutableStateFlow<NetworkResult<Login>>(NetworkResult.Empty())
    val loginResponse: StateFlow<NetworkResult<Login>> get() = _loginResponse

    init {
        authorizationCheck()
    }

    fun saveToken(token: String, rememberMe: Boolean) {
        sessionManagerUseCase.saveAuthToken(token = token, rememberMe)
    }

    fun login(userData: LoginRequest) = viewModelScope.launch {
        loginUseCase.login(userData).collect {
            _loginResponse.emit(it)
        }
    }

    private fun authorizationCheck() {
        sessionManagerUseCase.getAuthToken().let {
            isLoggedIn.value = !it.isNullOrEmpty()
        }
    }

}