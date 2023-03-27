package az.red.domain.repository.review

import az.red.domain.common.NetworkResult
import az.red.domain.model.review.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getCommentsOfProduct(productID: String): Flow<NetworkResult<List<Review>>>
}