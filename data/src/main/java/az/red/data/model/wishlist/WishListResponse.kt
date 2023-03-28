package az.red.data.model.wishlist

import az.red.data.model.auth.register.RegisterResponse
import az.red.data.model.product.ProductResponse


data class WishListResponse(
    val __v: Int,
    val _id: String,
    val customerId: RegisterResponse,
    val date: String,
    val products: List<ProductResponse>
)