package az.red.domain.usecase.cart

import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow

class AddToCartUseCase(private val repository:CartRepository) {
    suspend operator fun invoke(productId:String): Flow<NetworkResult<Cart>> {
        return repository.add(productId)
    }
}