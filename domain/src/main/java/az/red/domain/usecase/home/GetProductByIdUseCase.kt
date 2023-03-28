package az.red.domain.usecase.home

import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductByIdUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: String): Flow<NetworkResult<Product>> {
        return repository.getProductById(
            id = id
        )
    }
}