package az.red.data.model.auth.login

data class LoginResponse(
    val success: Boolean?,
    val token: String?,
    val password:String?,
    val loginOrEmail:String?
)