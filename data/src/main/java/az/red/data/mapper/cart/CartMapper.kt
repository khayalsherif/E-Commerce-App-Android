package az.red.data.mapper.cart

import az.red.data.model.cart.CartResponse
import az.red.domain.model.cart.Cart

class CartMapper {

    fun cartResponseToCart(cartResponse: CartResponse): Cart {
        return Cart(
            _id = cartResponse._id,
            products = cartResponse.products,
            customerId = cartResponse.customerId,
            date = cartResponse.date
        )
    }
}