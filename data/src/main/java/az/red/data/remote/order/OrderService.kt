package az.red.data.remote.order

import az.red.data.model.order.response.CreateOrderResponse
import az.red.data.model.order.response.OrderResponse
import az.red.data.remote.EndPoints
import az.red.domain.model.order.request.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {

    @POST(EndPoints.CREATE_ORDER)
    suspend fun createOrder(
        @Body orderRequest: OrderRequest
    ): Response<CreateOrderResponse>

    @GET(EndPoints.GET_CUSTOMER_ORDERS)
    suspend fun getCustomerOrders() : Response<List<OrderResponse>>
}