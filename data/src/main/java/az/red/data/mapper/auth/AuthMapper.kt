package az.red.data.mapper.auth

import az.red.data.model.auth.login.LoginRemoteRequest
import az.red.data.model.auth.login.LoginResponse
import az.red.data.model.auth.register.RegisterRemoteRequest
import az.red.data.model.auth.register.RegisterRequest
import az.red.data.model.auth.register.RegisterResponse
import az.red.domain.model.auth.login.Login
import az.red.domain.model.auth.login.LoginRequest
import az.red.domain.model.auth.register.Register

fun LoginResponse.loginResponseToLogin(): Login {
    return Login(
        success,
        token,
        password,
        loginOrEmail
    )
}

fun RegisterResponse.registerResponseToRegister(): Register {
    return Register(
        isAdmin,
        enabled,
        avatarUrl,
        customerNo,
        date,
        email,
        firstName,
        gender,
        lastName,
        login,
        password,
        telephone,
        message
    )
}

fun LoginRequest.loginRequestToLoginRemoteRequest(): LoginRemoteRequest {
    return LoginRemoteRequest(
        loginOrEmail,
        password
    )
}

fun RegisterRequest.registerRequestToRegisterRemoteRequest(): RegisterRemoteRequest {
    return RegisterRemoteRequest(
        firstName,
        lastName,
        login,
        email,
        password,
        isAdmin
    )
}

