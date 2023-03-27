package az.red.data.mapper.wishlist

import az.red.data.mapper.auth.registerResponseToRegister
import az.red.data.mapper.product.productResponseToProduct
import az.red.data.model.wishlist.WishListResponse
import az.red.domain.model.wishlist.WishList


fun WishListResponse.wishListResponseToWishList(): WishList {
    return WishList(
        _id,
        products.map { it.productResponseToProduct() },
        customerId.registerResponseToRegister(),
        date
    )
}