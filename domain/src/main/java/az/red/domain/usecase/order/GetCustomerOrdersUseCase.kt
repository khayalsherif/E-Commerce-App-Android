package az.red.domain.usecase.order

import az.red.domain.common.NetworkResult
import az.red.domain.model.order.response.GetCustomerOrder
import az.red.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetCustomerOrdersUseCase(
    private val orderRepository: OrderRepository
) {
    suspend fun getCustomerOrders(): Flow<NetworkResult<List<GetCustomerOrder>>> {
        return orderRepository.getCustomerOrders()
    }
}