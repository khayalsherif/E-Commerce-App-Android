package az.red.domain.model.order.request

import az.red.domain.model.auth.register.Register
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.order.request.DeliveryAddress

data class Order(
    val products : List<CartProduct>,
    val canceled : Boolean,
    val _id : String,
    val customerId : Register,
    val deliveryAddress : DeliveryAddress,
    val paymentInfo : String,
    val status : String,
    val email : String,
    val mobile : String,
    val letterSubject : String,
    val letterHtml : String,
    val orderNo : String,
    val totalSum : Double,
    val date : String,
    val __v : Int
)
