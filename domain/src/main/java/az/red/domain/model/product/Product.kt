package az.red.domain.model.product

data class Product(
    val _id: String,
    val categories: String,
    val color: String?,
    val currentPrice: Double,
    val date: String,
    val description: String?,
    val enabled: Boolean,
    val fabric: String?,
    val imageUrls: List<String>,
    val itemNo: String,
    val name: String,
    val previousPrice: Double?,
    val quantity: Int,
    val size: String?
){
    companion object {
        val NULL = Product(
            "",
            "",
            "",
            0.0,
            "",
            "",
            true,
            "",
            emptyList(),
            "",
            "",
            0.0,
            0,
            ""
        )
    }
}
