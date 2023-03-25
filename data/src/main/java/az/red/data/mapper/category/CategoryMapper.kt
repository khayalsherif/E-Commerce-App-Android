package az.red.data.mapper.category

import az.red.data.model.category.CategoryResponse
import az.red.data.util.capitalizeCustom
import az.red.domain.model.category.Category


fun CategoryResponse.categoryResponseToCategory(): Category {
    return Category(
        date = date,
        description = description,
        id = id,
        level = level,
        name = name.capitalizeCustom(),
        parentId = parentId,
        imgUrl = imgUrl,
        isSelected = false
    )
}
