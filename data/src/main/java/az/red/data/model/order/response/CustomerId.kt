package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class CustomerId(
    val isAdmin: Boolean,
    val enabled: Boolean,
    val paymentCardNumber: String,
    val address: String,
    @SerializedName("_id")
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val login: String,
    val password: String,
    val customerNo: String,
    val date: String,
    @SerializedName("__v")
    val v: Int
)