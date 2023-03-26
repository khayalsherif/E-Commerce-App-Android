package az.red.domain.model.order.response

data class ProductAvailabilityDetails(
    val productId : String,
    val itemNo : String,
    val orderedQuantity : Int,
    val realQuantity : Int,
    val diff : Int,
    val available : Boolean
)
