package az.red.data.repository.order

import az.red.data.common.handleApi
import az.red.data.mapper.order.OrderMapper
import az.red.data.remote.order.OrderService
import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.DomainOrder
import az.red.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val orderService: OrderService,
    private val orderMapper: OrderMapper
) : OrderRepository {

    override suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<DomainOrder>> {
        val request = orderService.createOrder(orderRequest)
        return handleApi(
            mapper = { orderMapper.orderResponseToDomainOrder(request.body()!!) },
            execute = { request }
        )
    }
}