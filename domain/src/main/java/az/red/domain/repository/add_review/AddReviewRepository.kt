package az.red.domain.repository.add_review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import kotlinx.coroutines.flow.Flow

interface AddReviewRepository {
    suspend fun addComment(addCommentBody: AddCommentRequest): Flow<NetworkResult<AddComment>>
}