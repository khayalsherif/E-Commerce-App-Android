package az.red.data.remote.review

import az.red.data.model.reviews.ReviewResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewService {

    @GET(EndPoints.GET_COMMENTS_OF_CUSTOMER)
    suspend fun getCommentsOfCustomer(
        @Path("productId") productID: String
    ): Response<List<ReviewResponse>>
}