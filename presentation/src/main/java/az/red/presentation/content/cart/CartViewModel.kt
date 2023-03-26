package az.red.presentation.content.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.cart.DeleteCart
import az.red.domain.model.order.request.OrderRequest
import az.red.domain.model.order.response.DomainOrder
import az.red.domain.usecase.cart.DeleteCartUseCase
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.domain.usecase.order.CreateOrderUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartUseCase: GetCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase
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
    fun getCart(){
        viewModelScope.launch {
            cartUseCase.getCart().collect{ networkResult ->
                when(networkResult){
                    is NetworkResult.Empty -> Log.i("CART_VIEW_MODEL","Empty")
                    is NetworkResult.Error -> Log.i("CART_VIEW_MODEL","Error")
                    is NetworkResult.Exception -> Log.i("CART_VIEW_MODEL","Exception")
                    is NetworkResult.Loading -> Log.i("CART_VIEW_MODEL","Loading")
                    is NetworkResult.Success -> {
                        isLoading.value = false
                        _cartResponse.emit(networkResult)
                    }
                }
            }
        }
    }

    fun deleteCart(){
        viewModelScope.launch {
            deleteCartUseCase.deleteCart().collect{networkResult ->
                when(networkResult){
                    is NetworkResult.Empty -> Log.i("DELETE_CART_VIEW_MODEL","Empty")
                    is NetworkResult.Error -> Log.i("DELETE_CART_VIEW_MODEL","Error")
                    is NetworkResult.Exception -> Log.i("DELETE_CART_VIEW_MODEL","Exception")
                    is NetworkResult.Loading -> Log.i("DELETE_CART_VIEW_MODEL","Loading")
                    is NetworkResult.Success -> {
                        isLoading.value = false
                        _deleteCartResponse.emit(networkResult)
                    }
                }
            }
        }
    }

    fun createOrder(orderRequest: OrderRequest){
        viewModelScope.launch {
            createOrderUseCase.createOrder(orderRequest).collect{networkResult ->
                when(networkResult){
                    is NetworkResult.Empty -> Log.i("CREATE_ORDER_VIEW_MODEL","Empty")
                    is NetworkResult.Error -> Log.i("CREATE_ORDER_VIEW_MODEL","Error")
                    is NetworkResult.Exception -> Log.i("CREATE_ORDER_VIEW_MODEL","Exception -> ${networkResult.message}")
                    is NetworkResult.Loading -> Log.i("CREATE_ORDER_VIEW_MODEL","Loading")
                    is NetworkResult.Success -> {
                        _createOrderResponse.emit(networkResult)
                    }
                }
            }
        }
    }
}