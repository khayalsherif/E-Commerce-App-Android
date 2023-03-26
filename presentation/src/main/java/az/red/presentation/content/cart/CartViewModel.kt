package az.red.presentation.content.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.cart.DeleteCart
import az.red.domain.model.order.request.DeliveryAddress
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.DomainOrder
import az.red.domain.usecase.cart.DeleteCartUseCase
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.domain.usecase.order.CreateOrderUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartUseCase: GetCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {

    val checkoutValue = MutableStateFlow("0.0")
    val isLoading = MutableStateFlow(true)

    private val _cartResponse =
        MutableStateFlow<NetworkResult<Cart>>(NetworkResult.Empty())
    val cartResponse: StateFlow<NetworkResult<Cart>> get() = _cartResponse

    private val _deleteCartResponse =
        MutableStateFlow<NetworkResult<DeleteCart>>(NetworkResult.Empty())
    val deleteCartResponse: StateFlow<NetworkResult<DeleteCart>> get() = _deleteCartResponse

    private val _createOrderResponse =
        MutableStateFlow<NetworkResult<DomainOrder>>(NetworkResult.Empty())
    val createOrderResponse: StateFlow<NetworkResult<DomainOrder>> get() = _createOrderResponse


    //Functions start
    fun getCart() {
        viewModelScope.launch {
            cartUseCase.getCart().collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Empty -> Log.i("CART_VIEW_MODEL", "Empty")
                    is NetworkResult.Error -> Log.i("CART_VIEW_MODEL", "Error")
                    is NetworkResult.Exception -> Log.i("CART_VIEW_MODEL", "Exception")
                    is NetworkResult.Loading -> Log.i("CART_VIEW_MODEL", "Loading")
                    is NetworkResult.Success -> {
                        isLoading.value = false
                        _cartResponse.emit(networkResult)
                    }
                }
            }
        }
    }

    fun deleteCart() {
        viewModelScope.launch {
            deleteCartUseCase.deleteCart().collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Empty -> Log.i("DELETE_CART_VIEW_MODEL", "Empty")
                    is NetworkResult.Error -> Log.i("DELETE_CART_VIEW_MODEL", "Error")
                    is NetworkResult.Exception -> Log.i("DELETE_CART_VIEW_MODEL", "Exception")
                    is NetworkResult.Loading -> Log.i("DELETE_CART_VIEW_MODEL", "Loading")
                    is NetworkResult.Success -> {
                        isLoading.value = false
                        _deleteCartResponse.emit(networkResult)
                    }
                }
            }
        }
    }

    fun createOrder(products: List<CartProduct>) {
        val userId = sessionManagerUseCase.getUserId()
        if (userId.isNullOrEmpty()) return
        val orderRequest = OrderRequest(
            userId,
            DeliveryAddress("Azerbaijan", "M.Hadi 256", "Baku", "1024"),
            "Baku AZ1000",
            "Credit card",
            "not shipped",
            "fsaliyeva96@gmail.com",
            "+380630000000",
            "Thank you for order! You are welcome!",
            "<h1>Your order is placed. OrderNo is ###.</h1><p>Have a good day!</p>",
            products
        )

        viewModelScope.launch {
            createOrderUseCase.createOrder(orderRequest).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Empty -> Log.i("CREATE_ORDER_VIEW_MODEL", "Empty")
                    is NetworkResult.Error -> Log.i("CREATE_ORDER_VIEW_MODEL", "Error")
                    is NetworkResult.Exception -> Log.i(
                        "CREATE_ORDER_VIEW_MODEL",
                        "Exception -> ${networkResult.message}"
                    )
                    is NetworkResult.Loading -> Log.i("CREATE_ORDER_VIEW_MODEL", "Loading")
                    is NetworkResult.Success -> {
                        _createOrderResponse.emit(networkResult)
                    }
                }
            }
        }
    }
}