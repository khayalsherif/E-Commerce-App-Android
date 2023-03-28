package az.red.domain.repository.review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun addComment(addCommentBody: AddCommentRequest): Flow<NetworkResult<AddComment>>
}