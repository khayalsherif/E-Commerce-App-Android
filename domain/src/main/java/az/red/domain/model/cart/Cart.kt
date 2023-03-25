package az.red.domain.model.cart

import az.red.domain.model.auth.register.Register

data class Cart(
    val _id : String,
    val products : List<CartProduct>,
    val customerId : Register,
    val date : String?
)
