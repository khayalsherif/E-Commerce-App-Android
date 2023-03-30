package az.red.domain.usecase.auth

import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.repository.auth.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repository: LoginRepository) {
    suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>> {
        return repository.login(loginData)
    }
}