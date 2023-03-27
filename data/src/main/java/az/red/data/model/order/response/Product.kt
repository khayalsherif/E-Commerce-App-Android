package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class Product(
    val enabled: Boolean,
    val imageUrls: List<String>,
    val quantity: Int,
    @SerializedName("_id")
    val id: String,
    val name: String,
    val currentPrice: Int,
    val previousPrice: Int,
    val categories: String,
    val fabric: String,
    val color: String,
    val size: String,
    val description: String,
    val itemNo: String,
    val date: String,
    @SerializedName("__v")
    val v: Int
)