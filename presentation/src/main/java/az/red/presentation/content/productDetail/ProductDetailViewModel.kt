package az.red.presentation.content.productDetail

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.Login
import az.red.domain.model.product.Product
import az.red.domain.usecase.home.GetProductByIdUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val useCase: GetProductByIdUseCase) : BaseViewModel() {

    private val _productResponse =
        MutableStateFlow<NetworkResult<Product>>(NetworkResult.Empty())
    val productResponse: StateFlow<NetworkResult<Product>> get() = _productResponse

    fun getProductDetail(id: String) = viewModelScope.launch {
        useCase.invoke(id).collect {
            _productResponse.emit(it)
        }
    }
}