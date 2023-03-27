package az.red.data.model.order.response


import com.google.gson.annotations.SerializedName

data class MailResult(
    val accepted: List<String>,
    val rejected: List<Any>,
    val envelopeTime: Int,
    val messageTime: Int,
    val messageSize: Int,
    val response: String,
    val envelope: Envelope,
    val messageId: String
)