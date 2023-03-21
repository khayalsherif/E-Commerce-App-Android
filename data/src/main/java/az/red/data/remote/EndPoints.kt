package az.red.data.remote

object EndPoints {
    private const val USER_BASE = "customers"

    // Auth
    const val LOGIN = "$USER_BASE/login"
    const val REGISTER = USER_BASE
    const val CURRENT_USER = "$USER_BASE/customer"
    const val UPDATE_USER = USER_BASE
    const val UPDATE_USER_PASSWORD = "$USER_BASE/password"
}