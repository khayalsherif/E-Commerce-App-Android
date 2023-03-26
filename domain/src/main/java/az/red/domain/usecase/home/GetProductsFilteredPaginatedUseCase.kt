package az.red.domain.usecase.home

import androidx.paging.PagingData
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import az.red.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsFilteredPaginatedUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Flow<PagingData<Product>> {
        return repository.getProductsFilteredPaginated(
            ProductListRequest()
        )
    }
}