package az.red.domain.model.order.request

data class DeliveryAddress(
    val country : String,
    val address : String,
    val city : String,
    val postal : String
){
    companion object {
        val NULL = DeliveryAddress(
            "",
            "",
            "",
            ""
        )
    }
}
