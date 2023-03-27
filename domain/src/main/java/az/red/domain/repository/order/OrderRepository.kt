package az.red.domain.repository.order

import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.DomainOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<DomainOrder>>
}