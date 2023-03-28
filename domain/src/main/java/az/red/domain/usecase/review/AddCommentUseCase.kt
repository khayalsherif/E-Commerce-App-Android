package az.red.domain.usecase.review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.repository.review.ReviewRepository
import kotlinx.coroutines.flow.Flow

class AddCommentUseCase(
    private val reviewRepository: ReviewRepository
) {
    suspend fun addComment(addCommentBody:AddCommentRequest):Flow<NetworkResult<AddComment>> {
        return reviewRepository.addComment(addCommentBody)
    }
}