package az.red.data.remote.category

import az.red.data.model.category.CategoryResponse
import az.red.data.remote.EndPoints
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET(EndPoints.CATEGORY)
    suspend fun getCategories(): Response<List<CategoryResponse>>
}