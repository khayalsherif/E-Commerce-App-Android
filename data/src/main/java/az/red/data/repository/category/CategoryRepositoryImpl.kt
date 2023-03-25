package az.red.data.repository.category

import az.red.data.common.handleApi
import az.red.data.mapper.category.categoryResponseToCategory
import az.red.data.model.category.CategoryResponse
import az.red.data.remote.category.CategoryService
import az.red.domain.common.NetworkResult
import az.red.domain.model.category.Category
import az.red.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val service: CategoryService) : CategoryRepository {
    override suspend fun get(): Flow<NetworkResult<List<Category>>> {
        return handleApi<List<CategoryResponse>, List<Category>>(mapper = { it.map { cr -> cr.categoryResponseToCategory() } }) { service.getCategories() }
    }
}