package az.red.data.repository.add_review

import az.red.data.common.handleApi
import az.red.data.mapper.add_review.AddReviewMapper
import az.red.data.remote.review.AddReviewService
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.repository.add_review.AddReviewRepository
import kotlinx.coroutines.flow.Flow

class AddReviewRepositoryImpl(
    private val reviewService: AddReviewService,
    private val reviewMapper: AddReviewMapper
) : AddReviewRepository {
    override suspend fun addComment(addCommentBody: AddCommentRequest): Flow<NetworkResult<AddComment>> {
        val request = reviewService.addComment(addCommentBody)
        return handleApi(
            mapper = { reviewMapper.addCommentResponseToAddComment(request.body()!!) },
            execute = {request}
        )
    }
}