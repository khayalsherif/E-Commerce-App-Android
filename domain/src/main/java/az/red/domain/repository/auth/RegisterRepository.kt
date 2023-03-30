package az.red.domain.repository.auth

import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.register.Register
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>>
}