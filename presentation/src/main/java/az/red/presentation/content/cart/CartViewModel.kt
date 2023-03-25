package az.red.presentation.content.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.CartProduct
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartUseCase: GetCartUseCase
) : BaseViewModel() {

    val checkoutValue = MutableStateFlow("0.0")

    private val _cartResponse =
        MutableStateFlow<NetworkResult<Cart>>(NetworkResult.Empty())
    val cartResponse: StateFlow<NetworkResult<Cart>> get() = _cartResponse

    fun getCart(){
        viewModelScope.launch {
            cartUseCase.getCart().collect{ networkResult ->
                when(networkResult){
                    is NetworkResult.Empty -> Log.i("SABIT","Loading")
                    is NetworkResult.Error -> Log.i("SABIT","Loading")
                    is NetworkResult.Exception -> Log.i("SABIT","Loading")
                    is NetworkResult.Loading -> {
                        Log.i("SABIT","Loading")
                    }
                    is NetworkResult.Success -> {
                        _cartResponse.emit(networkResult)
                    }
                }
            }
        }
    }
}