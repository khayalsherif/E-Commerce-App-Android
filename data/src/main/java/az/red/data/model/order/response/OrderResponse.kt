package az.red.data.model.order.response

import az.red.domain.model.order.response.ProductAvailabilityInfo

data class OrderResponse(
    val message : String,
    val productAvailibilityInfo : ProductAvailabilityInfo
)
