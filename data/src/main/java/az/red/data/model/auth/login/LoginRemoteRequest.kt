package az.red.data.model.auth.login

data class LoginRemoteRequest(
    val loginOrEmail: String,
    val password: String
)