package az.red.domain.usecase.auth

import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.register.Register
import az.red.domain.repository.auth.RegisterRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val repository: RegisterRepository) {

    suspend fun register(registerData: RegisterRequest): Flow<NetworkResult<Register>> {
        return repository.register(registerData)
    }
}