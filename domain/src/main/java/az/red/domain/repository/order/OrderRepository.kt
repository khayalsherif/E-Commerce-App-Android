package az.red.domain.repository.order

import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.CreateOrder
import az.red.domain.model.order.response.GetCustomerOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<CreateOrder>>
    suspend fun getCustomerOrders(): Flow<NetworkResult<List<GetCustomerOrder>>>
}