package az.red.data.model.review.response

import az.red.domain.model.auth.register.Register
import az.red.domain.model.product.Product

data class AddCommentResponse(
    val _id : String,
    val product: Product,
    val content : String,
    val customer : Register,
    val __v : Int
)
