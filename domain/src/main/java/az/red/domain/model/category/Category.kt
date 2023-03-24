package az.red.domain.model.category

data class Category(
    val date: String,
    val description: String,
    val id: String,
    val level: Int,
    val name: String,
    val parentId: String?,
    val imgUrl: String?,
    val isSelected:Boolean = false
)