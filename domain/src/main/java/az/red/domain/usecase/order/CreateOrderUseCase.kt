package az.red.domain.usecase.order

import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.CreateOrder
import az.red.domain.model.order.response.GetCustomerOrder
import az.red.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class CreateOrderUseCase(private val orderRepository: OrderRepository) {

    suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<CreateOrder>> {
        return orderRepository.createOrder(orderRequest)
    }

}