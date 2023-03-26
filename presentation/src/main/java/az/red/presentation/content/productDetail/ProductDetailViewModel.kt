package az.red.presentation.content.productDetail

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.usecase.home.GetProductByIdUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val useCase: GetProductByIdUseCase) : BaseViewModel() {

    fun getProductDetail(id: String) = viewModelScope.launch {
        useCase.invoke(id).collect {
            when (it) {
                is NetworkResult.Empty -> {}
                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {}
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    println(it.data)
                }
            }
        }
    }
}