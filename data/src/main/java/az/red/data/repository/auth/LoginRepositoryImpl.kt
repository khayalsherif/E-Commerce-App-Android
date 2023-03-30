package az.red.data.repository.auth

import az.red.data.common.handleApi
import az.red.data.mapper.auth.loginRequestToLoginRemoteRequest
import az.red.data.mapper.auth.loginResponseToLogin
import az.red.data.remote.auth.AuthService
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.repository.auth.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(private val service: AuthService) :
    LoginRepository {

    override suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>> {
        val request = service.login(loginData.loginRequestToLoginRemoteRequest())
        return handleApi(
            mapper = { it.loginResponseToLogin() },
            execute = { request }
        )
    }
}