package az.red.presentation.content.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.order.request.DeliveryAddress
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.CreateOrder
import az.red.domain.usecase.cart.DeleteCartUseCase
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.domain.usecase.order.CreateOrderUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartUseCase: GetCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val sessionManagerUseCase: SessionManagerUseCase
) : BaseViewModel() {
    val isLoading = MutableStateFlow(false)

    private val _cart = MutableStateFlow<Cart>(Cart.NULL)
    val cart = _cart.asStateFlow()

    private val _deleteCartItemResponse =
        MutableStateFlow<NetworkResult<Cart>>(NetworkResult.Empty())
    val deleteCartResponse = _deleteCartItemResponse.asStateFlow()

    private val _createOrderResponse =
        MutableSharedFlow<NetworkResult<CreateOrder>>()
    val createOrderResponse = _createOrderResponse.asSharedFlow()

    init {
        getCart()
    }

    //Functions start
    private fun getCart() {
        isLoading.value = true
        viewModelScope.launch {
            cartUseCase.getCart().collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Empty -> {
                        _cart.value = Cart.NULL
                        isLoading.value = false
                        Log.i("CART_VIEW_MODEL", "Empty")
                    }
                    is NetworkResult.Error -> {
                        Log.i("CART_VIEW_MODEL", "Error")
                    }
                    is NetworkResult.Exception -> {
                        Log.i("CART_VIEW_MODEL", "Exception ${networkResult.message}")
                    }
                    is NetworkResult.Loading -> Log.i("CART_VIEW_MODEL", "Loading")
                    is NetworkResult.Success -> {
                        networkResult.data?.let { _cart.value = it }
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun deleteSelectedCartItems() {
        isLoading.value = true
        viewModelScope.launch {
            val itemsToDelete = _cart.value.products.filter { it.isSelected }
            itemsToDelete.forEach {
                deleteCartUseCase.decreaseCartProduct(it.product._id).collect { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Empty -> Log.i("DELETE_CART_VIEW_MODEL", "Empty")
                        is NetworkResult.Error -> Log.i("DELETE_CART_VIEW_MODEL", "Error")
                        is NetworkResult.Exception -> Log.i("DELETE_CART_VIEW_MODEL", "Exception")
                        is NetworkResult.Loading -> Log.i("DELETE_CART_VIEW_MODEL", "Loading")
                        is NetworkResult.Success -> {
                            isLoading.value = false
                            _deleteCartItemResponse.value = networkResult
                            getCart()
                        }
                    }
                }
            }
        }
    }

    fun createOrder() {
        isLoading.value = true
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
            _cart.value.products
        )

        viewModelScope.launch {
            createOrderUseCase.createOrder(orderRequest).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Empty -> Log.i("CREATE_ORDER", "Empty")
                    is NetworkResult.Error -> {
                        Log.i("CREATE_ORDER", "Error")
                        isLoading.value = false
                    }
                    is NetworkResult.Exception -> {
                        Log.i(
                            "CREATE_ORDER",
                            "Exception -> ${networkResult.message}"
                        )
                        isLoading.value = false
                    }
                    is NetworkResult.Loading -> Log.i("CREATE_ORDER", "Loading")
                    is NetworkResult.Success -> {
                        Log.i("CREATE_ORDER", "Success")
                        if (networkResult.data?.message.isNullOrEmpty()) {
                            deleteCartUseCase.deleteCart().collect {
                                when (it) {
                                    is NetworkResult.Empty -> {
                                        Log.i("DELETE_CART", "Empty")
                                    }
                                    is NetworkResult.Error -> {
                                        Log.i("DELETE_CART", "Error")
                                    }
                                    is NetworkResult.Exception -> {
                                        Log.i(
                                            "DELETE_CART",
                                            "Exception -> ${networkResult.message}"
                                        )
                                    }
                                    is NetworkResult.Loading -> {
                                        Log.i("DELETE_CART", "Loading")
                                    }
                                    is NetworkResult.Success -> {
                                        Log.i("DELETE_CART", "Success")
                                    }
                                }
                                getCart()
                            }
                        }
                        _createOrderResponse.emit(networkResult)
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun setCartProductCheck(_id: String, isChecked: Boolean) {
        _cart.value =
            _cart.value.copy(products = _cart.value.products.map { it.copy(isSelected = if (it._id == _id) isChecked else it.isSelected) })
    }

    fun incrementProductQty(_id: String) {
        _cart.value =
            _cart.value.copy(products = _cart.value.products.map { it.copy(cartQuantity = if (it._id == _id) it.cartQuantity + 1 else it.cartQuantity) })
    }

    fun decrementProductQty(_id: String) {
        _cart.value = _cart.value.copy(products = _cart.value.products.map {
            it.copy(
                cartQuantity = if (it._id == _id) kotlin.math.max(
                    (it.cartQuantity - 1),
                    0
                ) else it.cartQuantity
            )
        })
    }
}