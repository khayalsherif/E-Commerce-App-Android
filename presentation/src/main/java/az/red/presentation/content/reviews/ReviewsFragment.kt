package az.red.presentation.content.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import az.red.domain.common.NetworkResult
import az.red.presentation.base.BaseFragment
import az.red.presentation.content.reviews.adapter.ReviewAdapter
import az.red.presentation.databinding.FragmentReviewsBinding
import coil.load
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ReviewsFragment : BaseFragment<FragmentReviewsBinding, ReviewsViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewsBinding
        get() = FragmentReviewsBinding::inflate
    override val kClass: KClass<ReviewsViewModel>
        get() = ReviewsViewModel::class
    private lateinit var navController: NavController

    private val args by navArgs<ReviewsFragmentArgs>()

    private val reviewAdapter by lazy { ReviewAdapter() }

    override val bindViews: FragmentReviewsBinding.() -> Unit = {
        navController = findNavController()
        binding.toolbar.setNavigationOnClickListener { navController.popBackStack() }
        viewModel.getReviews(args.productId)
        integrationRcView()

        textTitle.text = args.title
        textNewPrice.text = args.newPrice
        textOldPrice.text = args.oldPrice
        binding.imageView.load(args.imageUrl)

        lifecycleScope.launch {
            viewModel.reviewResponse.collect {
                when (it) {
                    is NetworkResult.Empty -> {
                    }
                    is NetworkResult.Error -> {
                        showToast(it.message!!)
                    }
                    is NetworkResult.Exception -> {
                        showToast(it.message!!)
                    }
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        reviewAdapter.setData(it.data!!)
                    }
                }
            }
        }
    }


    private fun integrationRcView() {
        binding.rcViewReview.adapter = reviewAdapter
        binding.rcViewReview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

}