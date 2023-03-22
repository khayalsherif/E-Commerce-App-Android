package az.red.data.mapper.auth

import az.red.data.model.auth.login.LoginRemoteRequest
import az.red.data.model.auth.register.RegisterRemoteRequest
import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.model.auth.login.LoginRequest

class AuthMapper {
    fun loginRequestToLoginRemoteRequest(loginRequest: LoginRequest): LoginRemoteRequest {
        return LoginRemoteRequest(
            loginOrEmail = loginRequest.loginOrEmail,
            password = loginRequest.password
        )
    }

    fun registerRequestToRegisterRemoteRequest(registerRequest: RegisterRequest): RegisterRemoteRequest {
        return RegisterRemoteRequest(
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            login = registerRequest.login,
            email = registerRequest.email,
            password = registerRequest.password,
            isAdmin = registerRequest.isAdmin
        )
    }
}
