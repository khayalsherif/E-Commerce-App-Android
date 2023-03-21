package az.red.data.remote.auth

import az.red.data.model.auth.login.LoginRemoteRequest
import az.red.data.model.auth.login.LoginResponse
import az.red.data.model.auth.register.RegisterRemoteRequest
import az.red.data.model.auth.register.RegisterResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(EndPoints.LOGIN)
    suspend fun login(@Body loginData: LoginRemoteRequest): Response<LoginResponse>

    @POST(EndPoints.REGISTER)
    suspend fun register(@Body registerData: RegisterRemoteRequest): Response<RegisterResponse>
}