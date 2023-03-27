package az.red.data.model.reviews

import az.red.data.model.product.ProductResponse
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("_id")
    val id: String,
    val product: ProductResponse,
    val content: String,
    val __v: Int
)
