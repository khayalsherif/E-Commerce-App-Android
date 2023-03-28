package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class Envelope(
    val from: String,
    val to: List<String>
)