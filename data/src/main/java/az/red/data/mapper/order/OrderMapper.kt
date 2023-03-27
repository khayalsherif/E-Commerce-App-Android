package az.red.data.mapper.order

import az.red.data.model.order.response.CreateOrderResponse
import az.red.data.model.order.response.OrderResponse
import az.red.domain.model.order.response.CreateOrder
import az.red.domain.model.order.response.GetCustomerOrder

class OrderMapper {

    fun createOrderResponseToDomainOrder(orderResponse: CreateOrderResponse): CreateOrder {
        return CreateOrder(
            message = orderResponse.message,
            productAvailibilityInfo = orderResponse.productAvailibilityInfo
        )
    }

    fun orderResponseToGetCustomerOrder(orderResponse: OrderResponse):GetCustomerOrder {
        return GetCustomerOrder(
            products = orderResponse.products,
            canceled = orderResponse.canceled,
            customerId = orderResponse.customerId,
            deliveryAddress = orderResponse.deliveryAddress,
            shipping = orderResponse.shipping,
            paymentInfo = orderResponse.paymentInfo,
            status = orderResponse.status,
            email = orderResponse.email,
            mobile = orderResponse.mobile,
            letterSubject = orderResponse.letterSubject,
            letterHtml = orderResponse.letterHtml,
            orderNo = orderResponse.orderNo,
            totalSum = orderResponse.totalSum,
            date = orderResponse.date
        )
    }
}