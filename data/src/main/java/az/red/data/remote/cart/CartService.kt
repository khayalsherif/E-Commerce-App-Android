package az.red.data.remote.cart

import az.red.data.model.cart.CartResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.GET

interface CartService {

   @GET(EndPoints.GET_CART)
   suspend fun getCart() : Response<CartResponse>
}