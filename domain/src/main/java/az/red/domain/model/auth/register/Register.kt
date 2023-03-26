package az.red.domain.model.auth.register

data class Register(
    val isAdmin: Boolean?,
    val enabled: Boolean?,
    val avatarUrl: String?,
    val customerNo: String?,
    val date: String?,
    val email: String?,
    val firstName: String?,
    val gender: String?,
    val lastName: String?,
    val login: String?,
    val password: String?,
    val telephone: String?,
    val message:String?,
    val _id : String?
)