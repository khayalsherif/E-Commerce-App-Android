package az.red.domain.model.order.request

data class MailResult(
    val accepted : List<String>,
    val rejected : List<String>,
    val envelope : Envelope
)
