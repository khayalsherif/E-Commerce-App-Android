package az.red.data.remote.cart

import az.red.data.model.cart.CartResponse
import az.red.data.model.cart.DeleteCartResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {

    @GET(EndPoints.GET_CART)
    suspend fun getCart(): Response<CartResponse>

    @DELETE(EndPoints.DELETE_CART)
    suspend fun deleteCart(): Response<DeleteCartResponse>

    @DELETE(EndPoints.DECREASE_CART_PRODUCT)
    suspend fun decreaseCartProduct(
        @Path("productId") productId: String
    ): Response<CartResponse>

    @PUT(EndPoints.ADD_CART_PRODUCT)
    suspend fun add(
        @Path("productId") productId: String
    ): Response<CartResponse>
}