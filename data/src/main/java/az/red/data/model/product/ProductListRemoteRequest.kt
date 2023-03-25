package az.red.data.model.product

data class ProductListRemoteRequest(
    val categories: String? = null,
    val color: String? = null,
    val fabric: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val size: String? = null,
    val userId: String? = null
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "categories" to categories,
            "color" to color,
            "fabric" to fabric,
            "name" to name,
            "userId" to userId,
            "brand" to brand,
            "size" to size
        ).filter { !it.value.isNullOrEmpty() } as Map<String, String>
    }
}