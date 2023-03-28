package az.red.data.model.order.response

import az.red.domain.model.order.response.ProductAvailabilityInfo

data class CreateOrderResponse(

    val order: Order?,
    val mailResult: MailResult?,
    val message : String?,
    val productAvailibilityInfo : ProductAvailabilityInfo?
)
