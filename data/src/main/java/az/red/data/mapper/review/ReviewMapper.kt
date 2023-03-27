package az.red.data.mapper.review

import az.red.data.mapper.product.productResponseToProduct
import az.red.data.model.reviews.ReviewResponse
import az.red.domain.model.review.Review


fun ReviewResponse.reviewResponseToReview(): Review =
    Review(
        id = this.id,
        product = this.product.productResponseToProduct(),
        content = this.content,
        __v = this.__v
    )
