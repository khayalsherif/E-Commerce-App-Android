package az.red.data.model.order.response

data class DeliveryAddress(
    val address: String,
    val city: String,
    val country: String,
    val postal: String
)