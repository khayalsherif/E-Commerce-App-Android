package az.red.presentation.content.reviews

import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.Review
import az.red.domain.usecase.review.ReviewUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewsViewModel(private val useCase: ReviewUseCase) : BaseViewModel() {

    private val _reviewResponse =
        MutableStateFlow<NetworkResult<List<Review>>>(NetworkResult.Empty())
    val reviewResponse: StateFlow<NetworkResult<List<Review>>> get() = _reviewResponse


    fun getReviews(productId: String) = viewModelScope.launch {
        useCase.invoke(productId).collect {
            _reviewResponse.emit(it)
        }
    }
}