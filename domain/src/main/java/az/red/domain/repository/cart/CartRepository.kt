package az.red.domain.repository.cart

import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.DeleteCart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCart() : Flow<NetworkResult<Cart>>
    suspend fun deleteCart() : Flow<NetworkResult<DeleteCart>>
    suspend fun decreaseCartProduct(productId : String) : Flow<NetworkResult<Cart>>
}