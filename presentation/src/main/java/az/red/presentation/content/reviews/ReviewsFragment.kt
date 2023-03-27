package az.red.presentation.content.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import az.red.domain.common.NetworkResult
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentReviewsBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ReviewsFragment : BaseFragment<FragmentReviewsBinding, ReviewsViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewsBinding
        get() = FragmentReviewsBinding::inflate
    override val kClass: KClass<ReviewsViewModel>
        get() = ReviewsViewModel::class
    private lateinit var navController: NavController

    private val args by navArgs<ReviewsFragmentArgs>()

    override val bindViews: FragmentReviewsBinding.() -> Unit = {
        navController = findNavController()
        viewModel.getReviews(args.productId)

        lifecycleScope.launch {
            viewModel.reviewResponse.collect {
                when(it){
                    is NetworkResult.Empty -> {
                    }
                    is NetworkResult.Error -> {
                        println(it.message)
                    }
                    is NetworkResult.Exception -> {
                        println(it.message)
                    }
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        println(it.data)
                    }
                }
            }
        }
    }
}