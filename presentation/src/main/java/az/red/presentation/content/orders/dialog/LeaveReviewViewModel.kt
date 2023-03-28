package az.red.presentation.content.orders.dialog

import android.util.Log
import androidx.lifecycle.viewModelScope
import az.red.domain.common.NetworkResult
import az.red.domain.model.review.request.AddCommentRequest
import az.red.domain.model.review.response.AddComment
import az.red.domain.usecase.review.AddCommentUseCase
import az.red.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaveReviewViewModel(
    private val addCommentUseCase: AddCommentUseCase
) : BaseViewModel() {

    private val _addCommentResponse = MutableStateFlow<AddComment>(AddComment.NULL)
    val addCommentResponse = _addCommentResponse.asStateFlow()

    fun addComment(
        addCommentRequest: AddCommentRequest,
        toast: (String) -> Unit,
        onSuccess: () -> Unit
    ){
        if(addCommentRequest.content.isBlank()){
            toast("Review cannot be empty")
            return
        }
        viewModelScope.launch {
            addCommentUseCase.addComment(addCommentRequest).collect{result ->
                when(result){
                    is NetworkResult.Empty -> Log.i("ADD_COMMENT","Empty")
                    is NetworkResult.Error -> Log.i("ADD_COMMENT","Empty")
                    is NetworkResult.Exception -> Log.i("ADD_COMMENT","Empty")
                    is NetworkResult.Loading -> Log.i("ADD_COMMENT","Empty")
                    is NetworkResult.Success -> {
                        if(addCommentRequest.content.isNotEmpty()){
                            _addCommentResponse.value.let {
                              it
                            }
                        }
                        onSuccess()
                    }
                }
            }
        }
    }
}