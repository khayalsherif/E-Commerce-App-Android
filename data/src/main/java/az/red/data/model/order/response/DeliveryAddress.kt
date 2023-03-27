package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class DeliveryAddress(
    val address: String,
    val city: String,
    val country: String,
    val postal: String
)