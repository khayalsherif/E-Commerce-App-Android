package az.red.data.remote.wishList

import az.red.data.model.wishlist.WishListResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WishlistService {
    @GET(EndPoints.WISHLIST)
    suspend fun getWishList(): Response<WishListResponse>

    @DELETE(EndPoints.WISHLIST_REMOVE_ITEM)
    suspend fun removeItem(@Path("productId") productId: String): Response<WishListResponse>

    @PUT(EndPoints.WISHLIST_ADD)
    suspend fun addItem(@Path("productId") productId: String): Response<WishListResponse>
}