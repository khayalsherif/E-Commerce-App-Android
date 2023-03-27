package az.red.data.model.order.response

import az.red.domain.model.auth.register.Register
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    val products : List<az.red.domain.model.product.Product>,
    val canceled : Boolean,
    @SerializedName("_id")
    val id : String,
    val customerId : Register,
    val deliveryAddress : az.red.domain.model.order.request.DeliveryAddress,
    val shipping : String,
    val paymentInfo : String,
    val status : String,
    val email : String,
    val mobile : String,
    val letterSubject : String,
    val letterHtml : String,
    val orderNo : String,
    val totalSum : Double,
    val date : String,
    @SerializedName("__v")
    val v : Int
)
