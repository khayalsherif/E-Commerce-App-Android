package az.red.domain.model.review

import az.red.domain.model.product.Product

data class Review(
    val id: String? = "",
    val product: Product,
    val content: String? = "",
    val customer: User,
    val __v: Int? = 0
)