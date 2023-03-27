package az.red.data.repository.wishlist

import az.red.data.common.handleApi
import az.red.data.mapper.wishlist.wishListResponseToWishList
import az.red.data.model.wishlist.WishListResponse
import az.red.data.remote.wishList.WishlistService
import az.red.domain.common.NetworkResult
import az.red.domain.model.wishlist.WishList
import az.red.domain.repository.WishListRepository
import kotlinx.coroutines.flow.Flow

class WishListRepositoryImpl(private val service: WishlistService) : WishListRepository {
    override suspend fun getWishlist(): Flow<NetworkResult<WishList>> =
        handleApi<WishListResponse, WishList>(mapper = { it.wishListResponseToWishList() }) { service.getWishList() }

}