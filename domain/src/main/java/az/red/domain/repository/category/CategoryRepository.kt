package az.red.domain.repository.category

import az.red.domain.common.NetworkResult
import az.red.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun get(): Flow<NetworkResult<List<Category>>>
}