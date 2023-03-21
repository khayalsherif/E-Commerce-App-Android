package az.red.domain.model.auth.login

data class LoginRequest(
    val loginOrEmail: String,
    val password: String
)