package az.red.presentation.content.register

import androidx.lifecycle.viewModelScope
import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.register.Register
import az.red.domain.usecase.auth.RegisterUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    private val _registerResponse =
        MutableStateFlow<NetworkResult<Register>>(NetworkResult.Empty())
    val registerResponse: StateFlow<NetworkResult<Register>> get() = _registerResponse

    fun saveToken(token: String, rememberMe: Boolean) {
        sessionManagerUseCase.saveAuthToken(token = token, rememberMe)
    }

    fun register(userData: RegisterRequest) = viewModelScope.launch {
        registerUseCase.register(userData).collect {
            _registerResponse.emit(it)
        }
    }

}