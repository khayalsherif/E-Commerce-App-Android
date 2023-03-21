package az.red.domain.usecase.auth

import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register
import az.red.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase(private val repository: AuthRepository) {
    suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>> {
        return repository.login(loginData)
    }

    suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>> {
        return repository.register(registerData)
    }
}