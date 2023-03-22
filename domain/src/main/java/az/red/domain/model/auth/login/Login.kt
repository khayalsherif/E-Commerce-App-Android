package az.red.domain.model.auth.login

data class Login(
    val success: Boolean?,
    val token: String?,
    val password:String?,
    val loginOrEmail:String?
)