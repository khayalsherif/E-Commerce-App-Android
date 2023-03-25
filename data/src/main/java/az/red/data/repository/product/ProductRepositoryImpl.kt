package az.red.data.repository.product

import az.red.data.common.handleApi
import az.red.data.mapper.product.productResponseToProduct
import az.red.data.mapper.product.toRemoteRequest
import az.red.data.model.product.ProductListResponse
import az.red.data.model.product.ProductResponse
import az.red.data.remote.product.ProductService
import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import az.red.domain.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val service: ProductService): ProductRepository {
    override suspend fun getProductsFiltered(
        request: ProductListRequest,
        count: Int?
    ): Flow<NetworkResult<List<Product>>> {
        return handleApi<ProductListResponse, List<Product>>(mapper = {it.products.map{ p ->p.productResponseToProduct()}}) {
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
}