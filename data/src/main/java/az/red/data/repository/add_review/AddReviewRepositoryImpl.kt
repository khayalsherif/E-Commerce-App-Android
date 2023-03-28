package az.red.data.repository.add_review

import az.red.data.common.handleApi
import az.red.data.mapper.review.ReviewMapper
import az.red.data.remote.review.ReviewService
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.repository.add_review.AddReviewRepository
import kotlinx.coroutines.flow.Flow

class AddReviewRepositoryImpl(
    private val reviewService: ReviewService,
    private val reviewMapper: ReviewMapper
) : AddReviewRepository {
    override suspend fun addComment(addCommentBody: AddCommentRequest): Flow<NetworkResult<AddComment>> {
        val request = reviewService.addComment(addCommentBody)
        return handleApi(
            mapper = { reviewMapper.addCommentResponseToAddComment(request.body()!!) },
            execute = {request}
        )
    }
}