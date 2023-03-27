package az.red.domain.usecase.cart

import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.DeleteCart
import az.red.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow

class DeleteCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend fun deleteCart(): Flow<NetworkResult<DeleteCart>> {
        return cartRepository.deleteCart()
    }
    suspend fun decreaseCartProduct(productId:String): Flow<NetworkResult<Cart>> {
        return cartRepository.decreaseCartProduct(productId)
    }
}