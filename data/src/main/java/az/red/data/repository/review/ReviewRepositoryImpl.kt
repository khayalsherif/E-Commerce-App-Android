package az.red.data.repository.review

import az.red.data.common.handleApi
import az.red.data.mapper.review.reviewResponseToReview
import az.red.data.remote.review.ReviewService
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.Review
import az.red.domain.repository.review.ReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl(private val service: ReviewService) : ReviewRepository {
    override suspend fun getCommentsOfProduct(productID: String): Flow<NetworkResult<List<Review>>> {
        return handleApi(
            mapper = { it.map { response -> response.reviewResponseToReview() } },
            execute = { service.getCommentsOfCustomer(productID) })
    }
}