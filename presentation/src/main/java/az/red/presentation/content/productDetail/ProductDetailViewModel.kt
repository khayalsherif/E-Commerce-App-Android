package az.red.presentation.content.productDetail

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.product.Product
import az.red.domain.usecase.home.GetProductByIdUseCase
import az.red.domain.usecase.home.GetProductsFilteredUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val useCase: GetProductByIdUseCase,
    private val getProductsFilteredUseCase: GetProductsFilteredUseCase
) : BaseViewModel() {

    private val _productResponse =
        MutableStateFlow<NetworkResult<Product>>(NetworkResult.Empty())
    val productResponse: StateFlow<NetworkResult<Product>> get() = _productResponse

    private val _similarProductResponse =
        MutableStateFlow<NetworkResult<List<Product>>>(NetworkResult.Empty())
    val similarProductResponse: StateFlow<NetworkResult<List<Product>>> get() = _similarProductResponse

    fun getProductDetail(id: String) = viewModelScope.launch {
        useCase.invoke(id).collect {
            _productResponse.emit(it)
        }
    }

    fun getSimilarProducts(category: String) = viewModelScope.launch {
        getProductsFilteredUseCase.invoke(categories = category).collect {
            _similarProductResponse.emit(it)
        }
    }
}