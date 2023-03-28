package az.red.data.remote.review

import az.red.data.model.review.response.AddCommentResponse
import az.red.data.remote.EndPoints
import az.red.domain.model.review.request.AddCommentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewService {

    @POST(EndPoints.ADD_COMMENT)
    suspend fun addComment(
        @Body addCommentRequest: AddCommentRequest
    ) : Response<AddCommentResponse>
}