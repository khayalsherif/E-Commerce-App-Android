package az.red.domain.repository.auth

import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>>
    suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>>
}