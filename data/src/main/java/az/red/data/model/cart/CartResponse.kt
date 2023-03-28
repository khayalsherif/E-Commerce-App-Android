package az.red.data.model.cart

import az.red.domain.model.auth.register.Register
import az.red.domain.model.cart.CartProduct

data class CartResponse(
    val _id : String,
    val products : List<CartProduct>,
    val customerId : Register,
    val date : String?,
    val __v : Int
)