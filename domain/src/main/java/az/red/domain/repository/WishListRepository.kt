package az.red.domain.repository

import az.red.domain.common.NetworkResult
import az.red.domain.model.wishlist.WishList
import kotlinx.coroutines.flow.Flow

interface WishListRepository {
    suspend fun getWishlist(): Flow<NetworkResult<WishList>>
    suspend fun removeItem(productId: String): Flow<NetworkResult<WishList>>
}