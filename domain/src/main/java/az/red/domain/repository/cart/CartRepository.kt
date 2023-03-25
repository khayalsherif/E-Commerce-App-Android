package az.red.domain.repository.cart

import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCart() : Flow<NetworkResult<Cart>>
}