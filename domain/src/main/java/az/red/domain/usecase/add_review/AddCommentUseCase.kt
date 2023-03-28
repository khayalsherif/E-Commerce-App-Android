package az.red.domain.usecase.add_review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.repository.add_review.AddReviewRepository
import kotlinx.coroutines.flow.Flow

class AddCommentUseCase(
    private val reviewRepository: AddReviewRepository
) {
    suspend fun addComment(addCommentBody:AddCommentRequest):Flow<NetworkResult<AddComment>> {
        return reviewRepository.addComment(addCommentBody)
    }
}