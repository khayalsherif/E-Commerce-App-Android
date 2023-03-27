package az.red.domain.model.cart

import az.red.domain.model.product.Product

data class CartProduct(
    val _id : String,
    val product : Product,
    val cartQuantity : Int,
    var isSelected: Boolean = false
)
