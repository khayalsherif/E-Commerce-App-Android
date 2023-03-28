package az.red.data.repository.order

import az.red.data.common.handleApi
import az.red.data.mapper.order.OrderMapper
import az.red.data.model.order.response.CreateOrderResponse
import az.red.data.remote.order.OrderService
import az.red.domain.common.NetworkResult
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.CreateOrder
import az.red.domain.model.order.response.GetCustomerOrder
import az.red.domain.repository.order.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val orderService: OrderService,
    private val orderMapper: OrderMapper
) : OrderRepository {

    override suspend fun createOrder(orderRequest: OrderRequest): Flow<NetworkResult<CreateOrder>> {
        val request = orderService.createOrder(orderRequest)
        return handleApi<CreateOrderResponse, CreateOrder>(
            mapper = { orderMapper.createOrderResponseToDomainOrder(request.body()!!) },
            execute = { request }
        )
    }

    override suspend fun getCustomerOrders(): Flow<NetworkResult<List<GetCustomerOrder>>> {
        val request = orderService.getCustomerOrders()
        return handleApi(
            mapper = { it.map { order -> orderMapper.orderResponseToGetCustomerOrder(order) } },
            execute = { request }
        )
    }
}