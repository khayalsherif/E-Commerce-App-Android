package az.red.domain.model.review.request

data class AddCommentRequest(
    val product : String,
    val content : String
)
