package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class Order(
    val products: List<Product>,
    val canceled: Boolean,
    @SerializedName("_id")
    val id: String,
    val customerId: CustomerId,
    val deliveryAddress: DeliveryAddress,
    val email: String,
    val letterHtml: String,
    val letterSubject: String,
    val mobile: String,
    val paymentInfo: String,
    val shipping: String,
    val status: String,
    val orderNo: String,
    val totalSum: Int,
    val date: String,
    @SerializedName("__v")
    val v: Int
)