package az.red.data.repository.auth

import az.red.data.common.handleApi
import az.red.data.mapper.auth.loginRequestToLoginRemoteRequest
import az.red.data.mapper.auth.loginResponseToLogin
import az.red.data.mapper.auth.registerRequestToRegisterRemoteRequest
import az.red.data.mapper.auth.registerResponseToRegister
import az.red.data.model.auth.register.RegisterRequest
import az.red.data.remote.auth.AuthService
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register
import az.red.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val service: AuthService) :
    AuthRepository {
    override suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>> {
        val request = service.login(loginData.loginRequestToLoginRemoteRequest())
        return handleApi(
            mapper = { it.loginResponseToLogin() },
            execute = { request }
        )
    }

    override suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>> {
        val request = service.register(registerData.registerRequestToRegisterRemoteRequest())
        return handleApi(
            mapper = { it.registerResponseToRegister() },
            execute = { request }
        )
    }

}