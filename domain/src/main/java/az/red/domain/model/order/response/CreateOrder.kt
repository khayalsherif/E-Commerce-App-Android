package az.red.domain.model.order.response


data class CreateOrder(
    val message : String?,
    val productAvailibilityInfo : ProductAvailabilityInfo?,
)