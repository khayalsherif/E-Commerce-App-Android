package az.red.domain.repository.product

import androidx.paging.PagingData
import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductsFiltered(request: ProductListRequest, count:Int? = null): Flow<NetworkResult<List<Product>>>
    suspend fun getProductsFilteredPaginated(request: ProductListRequest): Flow<PagingData<Product>>
    suspend fun getProductById(id:String): Flow<NetworkResult<Product>>
}