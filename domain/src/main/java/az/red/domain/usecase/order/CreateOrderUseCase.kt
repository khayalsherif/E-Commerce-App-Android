package az.red.domain.usecase.order

import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.DomainOrder
import az.red.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class CreateOrderUseCase(private val orderRepository: OrderRepository) {

    suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<DomainOrder>> {
        return orderRepository.createOrder(orderRequest)
    }

}