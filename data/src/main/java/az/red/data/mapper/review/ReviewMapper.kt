package az.red.data.mapper.review

import az.red.data.mapper.product.productResponseToProduct
import az.red.data.model.reviews.ReviewResponse
import az.red.data.model.reviews.UserResponse
import az.red.domain.model.review.Review
import az.red.domain.model.review.User


fun ReviewResponse.reviewResponseToReview(): Review =
    Review(
        id = this.id,
        product = this.product.productResponseToProduct(),
        content = this.content,
        customer = this.customer.userResponseToUser(),
        __v = this.__v
    )


fun UserResponse.userResponseToUser(): User =
    User(
        id,
        isAdmin,
        enabled,
        avatarUrl,
        customerNo,
        date,
        email,
        firstName,
        gender,
        lastName,
        login,
        password,
        telephone,
        birthdate,
        v
    )