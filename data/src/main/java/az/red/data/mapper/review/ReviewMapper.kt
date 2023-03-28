package az.red.data.mapper.review

import az.red.data.model.review.response.AddCommentResponse
import az.red.domain.model.review.response.AddComment

class ReviewMapper {

    fun addCommentResponseToAddComment(addCommentResponse: AddCommentResponse):AddComment {
        return AddComment(
            _id = addCommentResponse._id,
            product = addCommentResponse.product,
            content = addCommentResponse.content,
            customer = addCommentResponse.customer
        )
    }
}