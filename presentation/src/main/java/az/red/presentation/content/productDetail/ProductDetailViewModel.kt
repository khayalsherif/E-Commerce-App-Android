package az.red.presentation.content.productDetail

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.product.Product
import az.red.domain.usecase.cart.AddToCartUseCase
import az.red.domain.usecase.home.GetProductByIdUseCase
import az.red.domain.usecase.home.GetProductsFilteredUseCase
import az.red.domain.usecase.wishList.AddToWishListUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsFilteredUseCase: GetProductsFilteredUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToWishListUseCase: AddToWishListUseCase
) : BaseViewModel() {

    private val _productResponse =
        MutableStateFlow<NetworkResult<Product>>(NetworkResult.Empty())
    val productResponse: StateFlow<NetworkResult<Product>> get() = _productResponse

    private val _similarProductResponse =
        MutableStateFlow<NetworkResult<List<Product>>>(NetworkResult.Empty())
    val similarProductResponse: StateFlow<NetworkResult<List<Product>>> get() = _similarProductResponse

    private val _addToCartResponse =
        MutableStateFlow<NetworkResult<Cart>>(NetworkResult.Empty())
    val addToCartResponse = _addToCartResponse.asStateFlow()

    fun getProductDetail(id: String) = viewModelScope.launch {
        getProductByIdUseCase(id).collect {
            _productResponse.emit(it)
        }
    }

    fun getSimilarProducts(category: String) = viewModelScope.launch {
        getProductsFilteredUseCase.invoke(categories = category).collect {
            _similarProductResponse.emit(it)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            _productResponse.value.data?._id?.let {
                addToCartUseCase(productId = it).collect {
                    _addToCartResponse.emit(it)
                }
            }
        }
    }
    fun addToWishList(productId: String) {
        viewModelScope.launch {
            addToWishListUseCase(productId).collect {
                when (it) {
                    is NetworkResult.Empty -> println("Empty")
                    is NetworkResult.Error -> println("Error")
                    is NetworkResult.Exception -> println("Exception ${it.message}")
                    is NetworkResult.Loading -> println("Loading")
                    is NetworkResult.Success ->  println("Success")
                }
            }
        }
    }
}