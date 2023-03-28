package az.red.domain.model.review.response

import az.red.domain.model.auth.register.Register
import az.red.domain.model.product.Product

data class AddComment(
    val _id : String,
    val product: Product,
    val content : String,
    val customer : Register,
){
    companion object {
        val NULL = AddComment(
            _id = "",
            product = Product.NULL,
            content = "",
            customer = Register.NULL
        )
    }
}
