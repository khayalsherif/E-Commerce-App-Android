package az.red.domain.model.order.response

import az.red.domain.model.auth.register.Register
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.order.request.DeliveryAddress
import az.red.domain.model.product.Product

data class GetCustomerOrder(
    val products : List<CartProduct>,
    val canceled : Boolean,
    val customerId : Register,
    val deliveryAddress : DeliveryAddress,
    val shipping : String,
    val paymentInfo : String,
    val status : String,
    val email : String,
    val mobile : String,
    val letterSubject : String,
    val letterHtml : String,
    val orderNo : String,
    val totalSum : Double,
    val date : String,
){
    companion object {
        val NULL = GetCustomerOrder(
            emptyList(),
            false,
            Register.NULL,
            DeliveryAddress.NULL,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0.0,
            ""
        )
    }
}
