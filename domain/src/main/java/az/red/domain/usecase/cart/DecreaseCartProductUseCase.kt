package az.red.domain.usecase.cart

import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow

class DecreaseCartProductUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun decreaseCartProduct(productId : String): Flow<NetworkResult<Cart>> {
        return cartRepository.decreaseCartProduct(productId)
    }
}