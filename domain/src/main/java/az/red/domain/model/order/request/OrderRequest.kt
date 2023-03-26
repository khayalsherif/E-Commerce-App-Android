package az.red.domain.model.order.request

import az.red.domain.model.cart.CartProduct

data class OrderRequest(
    val customerId : String,
    val deliveryAddress : DeliveryAddress,
    val shipping : String,
    val paymentInfo : String,
    val status : String,
    val email : String,
    val mobile : String,
    val letterSubject : String,
    val letterHtml : String,
    val products : List<CartProduct>
)
