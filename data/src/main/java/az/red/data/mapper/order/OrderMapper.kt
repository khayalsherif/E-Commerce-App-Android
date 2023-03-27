package az.red.data.mapper.order

import az.red.data.model.order.response.OrderResponse
import az.red.domain.model.order.response.DomainOrder

class OrderMapper {

    fun orderResponseToDomainOrder(orderResponse: OrderResponse): DomainOrder {
        return DomainOrder(
            message = orderResponse.message,
            productAvailibilityInfo = orderResponse.productAvailibilityInfo
        )
    }
}