package az.red.data.model.auth.register

data class RegisterRemoteRequest(
    val firstName: String,
    val lastName: String,
    val login: String,
    val email: String,
    val password: String,
    val isAdmin: Boolean
)
