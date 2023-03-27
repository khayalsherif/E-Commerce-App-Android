package az.red.presentation.content.wishList

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.wishlist.WishList
import az.red.domain.usecase.wishList.GetWishListUseCase
import az.red.domain.usecase.wishList.RemoveWishListItemUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WishListViewModel(private val getWishListUseCase: GetWishListUseCase,
                        private val removeWishListItemUseCase: RemoveWishListItemUseCase
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _wishList = MutableStateFlow(WishList.NULL)
    val wishList = _wishList.asStateFlow()

    init {
        getWishList()
    }

    private fun getWishList(searchQuery: String? = null) {
        _isLoading.value = true
        viewModelScope.launch {
            getWishListUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Empty -> {
                        Log.e("GET_WISH_LIST", "Empty")
                        _wishList.value = WishList.NULL
                    }
                    is NetworkResult.Error -> {
                        Log.e("GET_WISH_LIST", "Error ${result.data?.message}")
                    }
                    is NetworkResult.Exception -> {
                        result.message?.let { Log.e("GET_WISH_LIST", it) }
                    }
                    is NetworkResult.Loading -> {
                        Log.e("GET_WISH_LIST", "Loading ...")
                    }
                    is NetworkResult.Success -> {
                        Log.e("GET_WISH_LIST", "Success")
                        result.data?.let { _wishList.value = it }
                    }
                }
                _isLoading.value = false
            }
        }
    }

    fun addToCart(productId: String) {

    }

    fun removeFromWishList(productId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            removeWishListItemUseCase(productId).collect{result ->
                when (result) {
                    is NetworkResult.Empty -> {
                        Log.e("REMOVE_FROM_WISH_LIST", "Empty")
                    }
                    is NetworkResult.Error -> {
                        Log.e("REMOVE_FROM_WISH_LIST", "Error ${result.data?.message}")
                    }
                    is NetworkResult.Exception -> {
                        result.message?.let { Log.e("REMOVE_FROM_WISH_LIST", it) }
                    }
                    is NetworkResult.Loading -> {
                        Log.e("REMOVE_FROM_WISH_LIST", "Loading ...")
                    }
                    is NetworkResult.Success -> {
                        Log.e("REMOVE_FROM_WISH_LIST", "Success")
                    }
                }
                getWishList()
            }
        }
    }
}