package az.red.data.repository.product

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import az.red.data.common.handleApi
import az.red.data.mapper.product.productResponseToProduct
import az.red.data.mapper.product.toRemoteRequest
import az.red.data.model.product.ProductListResponse
import az.red.data.remote.product.ProductService
import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import az.red.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val service: ProductService) : ProductRepository {
    override suspend fun getProductsFiltered(
        request: ProductListRequest,
        count: Int?
    ): Flow<NetworkResult<List<Product>>> {
        return handleApi<ProductListResponse, List<Product>>(mapper = { it.products.map { p -> p.productResponseToProduct() } }) {
            count?.let {
                service.getProductsFiltered(
                    query = request.toRemoteRequest().toMap(),
                    startPage = 1,
                    perPage = it
                )
            } ?: service.getProductsFiltered(
                query = request.toRemoteRequest().toMap()
            )
        }
    }

    override suspend fun getProductsFilteredPaginated(
        request: ProductListRequest
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ProductListPagingSource(service = service, request)
            }
        ).flow
    }

    override suspend fun getProductById(id: String): Flow<NetworkResult<Product>> {
        return handleApi(mapper = { it.productResponseToProduct() }) {
            service.getProductById(id)
        }
    }
}


const val NETWORK_PAGE_SIZE = 8