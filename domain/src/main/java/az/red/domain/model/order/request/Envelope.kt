package az.red.domain.model.order.request

data class Envelope(
    val from : String,
    val to : List<String>
)
