package az.red.presentation.content.orders

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.order.response.GetCustomerOrder
import az.red.domain.usecase.order.GetCustomerOrdersUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val getCustomerOrdersUseCase: GetCustomerOrdersUseCase
) : BaseViewModel() {

    val isLoading = MutableStateFlow(false)
    val ongoingExpandState = MutableStateFlow(false)
    val completedExpandState = MutableStateFlow(false)
    val cancelledExpandState = MutableStateFlow(false)

    private val _getCustomerOrderResponse =
        MutableStateFlow<List<GetCustomerOrder>>(emptyList())
    val getCustomerOrderResponse = _getCustomerOrderResponse.asStateFlow()

    init {
        getCustomerOrders()
    }

    fun getCustomerOrders(){
        viewModelScope.launch {
            getCustomerOrdersUseCase.getCustomerOrders().collect{networkResult ->
                when(networkResult){
                    is NetworkResult.Empty -> {
                        _getCustomerOrderResponse.value = emptyList()
                        isLoading.value = false
                        Log.i("GET_CUSTOMER_ORDERS","Empty")
                    }
                    is NetworkResult.Error -> Log.i("GET_CUSTOMER_ORDERS","Error")
                    is NetworkResult.Exception -> Log.i("GET_CUSTOMER_ORDERS",networkResult.message!!)
                    is NetworkResult.Loading -> Log.i("GET_CUSTOMER_ORDERS","Loading")
                    is NetworkResult.Success ->{
                        Log.i("GET_CUSTOMER_ORDERS",networkResult.data!!.size.toString())
                        _getCustomerOrderResponse.value = networkResult.data!!
                        isLoading.value = false
                    }
                }
            }
        }
    }

    fun expandOngoingCard() {
        ongoingExpandState.value = !ongoingExpandState.value
    }
    fun expandCompleteCard() {
        completedExpandState.value = !completedExpandState.value
    }
    fun expandCancelledCard() {
        cancelledExpandState.value = !cancelledExpandState.value
    }

}