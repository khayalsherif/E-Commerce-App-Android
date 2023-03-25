package az.red.domain.repository.product

import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductsFiltered(request: ProductListRequest, count:Int? = null): Flow<NetworkResult<List<Product>>>
}