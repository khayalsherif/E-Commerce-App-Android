package az.red.data.remote.product

import az.red.data.model.product.ProductListResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ProductService {
    @GET(EndPoints.PRODUCTS_FILTERED)
    suspend fun getProductsFilteredPaging(
        @QueryMap query: Map<String, String>,
        @Query("startPage") startPage: Int,
        @Query("perPage") perPage: Int
    ): ProductListResponse

    @GET(EndPoints.PRODUCTS_FILTERED)
    suspend fun getProductsFiltered(
        @QueryMap query: Map<String, String>,
        @Query("startPage") startPage: Int? = null,
        @Query("perPage") perPage: Int? = null
    ): Response<ProductListResponse>

}