package az.red.data.repository.review

import az.red.data.common.handleApi
import az.red.data.mapper.review.ReviewMapper
import az.red.data.remote.review.ReviewService
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.repository.review.ReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl(
    private val reviewService: ReviewService,
    private val reviewMapper: ReviewMapper
) : ReviewRepository {
    override suspend fun addComment(addCommentBody: AddCommentRequest): Flow<NetworkResult<AddComment>> {
        val request = reviewService.addComment(addCommentBody)
        return handleApi(
            mapper = { reviewMapper.addCommentResponseToAddComment(request.body()!!) },
            execute = {request}
        )
    }
}