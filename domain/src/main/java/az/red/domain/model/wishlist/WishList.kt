package az.red.domain.model.wishlist

import az.red.domain.model.auth.register.Register
import az.red.domain.model.product.Product

data class WishList(
    val id: String?,
    val products: List<Product>,
    val customerId: Register?,
    val date: String?,
    val message: String? = null
){
    companion object {
        val NULL = WishList("", emptyList(), Register.NULL, "")
    }
}
