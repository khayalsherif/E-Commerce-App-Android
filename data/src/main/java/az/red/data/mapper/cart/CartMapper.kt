package az.red.data.mapper.cart

import az.red.data.model.cart.CartResponse
import az.red.data.model.cart.DeleteCartResponse
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.DeleteCart

class CartMapper {

    fun cartResponseToCart(cartResponse: CartResponse): Cart {
        return Cart(
            _id = cartResponse._id,
            products = cartResponse.products,
            customerId = cartResponse.customerId,
            date = cartResponse.date
        )
    }

    fun deleteCartResponseToDeleteCart(deleteCartResponse: DeleteCartResponse): DeleteCart {
        return DeleteCart(
            message = deleteCartResponse.message
        )
    }
}