package az.red.domain.model.order.response


data class DomainOrder(
    val message : String?,
    val productAvailibilityInfo : ProductAvailabilityInfo?,
)