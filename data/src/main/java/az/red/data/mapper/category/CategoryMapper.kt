package az.red.data.mapper.category

import az.red.data.model.category.CategoryResponse
import az.red.domain.model.category.Category


fun CategoryResponse.categoryResponseToCategory(): Category {
    return Category(
        date = date,
        description = description,
        id = id,
        level = level,
        name = name,
        parentId = parentId,
        imgUrl = imgUrl,
        isSelected = false
    )
}
