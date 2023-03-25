package az.red.data.repository.cart

import az.red.data.common.handleApi
import az.red.data.mapper.cart.CartMapper
import az.red.data.model.cart.CartResponse
import az.red.data.remote.cart.CartService
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.repository.cart.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val cartService: CartService,
    private val cartMapper: CartMapper
) : CartRepository {

    override suspend fun getCart(): Flow<NetworkResult<Cart>> {
        val request = cartService.getCart()
        return handleApi(
            mapper = { cartMapper.cartResponseToCart(request.body()!!) },
            execute = { cartService.getCart() }
        )
    }
}