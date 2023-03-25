package az.red.domain.model.product

data class ProductListRequest(
    val categories: String? = null,
    val color: String? = null,
    val fabric: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val size: String? = null,
    val userId: String? = null
)