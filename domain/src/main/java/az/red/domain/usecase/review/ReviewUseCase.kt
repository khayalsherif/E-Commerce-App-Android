package az.red.domain.usecase.review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.Review
import az.red.domain.repository.review.ReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewUseCase(private val repository: ReviewRepository) {
    suspend fun invoke(productId: String): Flow<NetworkResult<List<Review>>> {
        return repository.getCommentsOfProduct(productId)
    }
}