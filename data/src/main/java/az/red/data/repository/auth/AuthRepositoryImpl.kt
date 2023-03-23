package az.red.data.repository.auth

import az.red.data.common.handleApi
import az.red.data.mapper.auth.AuthMapper
import az.red.data.model.auth.register.RegisterRequest
import az.red.data.remote.auth.AuthService
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register
import az.red.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val service: AuthService, private val mapper: AuthMapper) :
    AuthRepository {
    override suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>> {
        val request = service.login(mapper.loginRequestToLoginRemoteRequest(loginData))
        return handleApi(
            mapper = { mapper.loginResponseToLogin(request.body()!!) },
            execute = { request }
        )
    }

    override suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>> {
        val request = service.register(mapper.registerRequestToRegisterRemoteRequest(registerData))
        return handleApi(
            mapper = { mapper.registerResponseToRegister(request.body()!!) },
            execute = { request }
        )
    }

}