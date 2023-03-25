package az.red.domain.usecase.home

import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import az.red.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsFilteredUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(categories: String? = null): Flow<NetworkResult<List<Product>>> {
        return repository.getProductsFiltered(
            ProductListRequest(categories = categories),
            count = 5
        )
    }
}