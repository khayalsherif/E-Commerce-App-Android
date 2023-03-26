package az.red.data.mapper.auth

import az.red.data.model.auth.login.LoginRemoteRequest
import az.red.data.model.auth.login.LoginResponse
import az.red.data.model.auth.register.RegisterRemoteRequest
import az.red.data.model.auth.register.RegisterRequest
import az.red.data.model.auth.register.RegisterResponse
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register

class AuthMapper {
    fun loginResponseToLogin(loginResponse: LoginResponse): Login {
        return Login(
            success = loginResponse.success,
            token = loginResponse.token,
            password = loginResponse.password,
            loginOrEmail = loginResponse.loginOrEmail
        )
    }

    fun registerResponseToRegister(registerResponse: RegisterResponse): Register {
        return Register(
            isAdmin = registerResponse.isAdmin,
            enabled = registerResponse.enabled,
            avatarUrl = registerResponse.avatarUrl,
            customerNo = registerResponse.customerNo,
            date = registerResponse.date,
            email = registerResponse.email,
            firstName = registerResponse.firstName,
            gender = registerResponse.gender,
            lastName = registerResponse.lastName,
            login = registerResponse.login,
            password = registerResponse.password,
            telephone = registerResponse.telephone,
            message = registerResponse.message,
            _id = registerResponse._id
        )
    }

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
