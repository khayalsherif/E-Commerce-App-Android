package az.red.data.remote.wishList

import az.red.data.model.wishlist.WishListResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.GET

interface WishlistService {
    @GET(EndPoints.WISHLIST)
    suspend fun getWishList():Response<WishListResponse>
}