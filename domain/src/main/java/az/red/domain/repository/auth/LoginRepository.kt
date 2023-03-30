package az.red.domain.repository.auth

import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginData: LoginRequest): Flow<NetworkResult<Login>>
}