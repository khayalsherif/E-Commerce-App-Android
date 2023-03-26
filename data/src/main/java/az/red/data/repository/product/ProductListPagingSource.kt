package az.red.data.repository.product

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import az.red.data.mapper.product.productResponseToProduct
import az.red.data.remote.product.ProductService
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest
import retrofit2.HttpException
import java.io.IOException

class ProductListPagingSource(
    private val service: ProductService,
    private val request: ProductListRequest
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: PRODUCT_LIST_STARTING_PAGE_INDEX
        return try {
            val response = service.getProductsFilteredPaging(request.toMap(), position, params.loadSize)

            val products = response.products.map { it.productResponseToProduct() }
            val nextKey = if (products.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = products,
                prevKey = if (position == PRODUCT_LIST_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            Log.e("PRODUCT_LIST", exception.stackTraceToString())
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            Log.e("PRODUCT_LIST", exception.stackTraceToString())
            LoadResult.Error(exception)
        }
    }

    private val PRODUCT_LIST_STARTING_PAGE_INDEX = 1
}

