package az.red.presentation.content.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.content.home.adapter.CategoryListItemAdapter
import az.red.presentation.content.home.adapter.ProductListItemAdapter
import az.red.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    override val kClass: KClass<HomeViewModel>
        get() = HomeViewModel::class

    private val categoryListItemAdapter by lazy {
        CategoryListItemAdapter {
            viewModel.selectCategory(it)
        }
    }

    private val productListItemAdapter by lazy {
        ProductListItemAdapter { //Add to wih list
            showToast("added to wishlist")
        }
    }

    override val bindViews: FragmentHomeBinding.() -> Unit = {

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        rvSubCategoryCards.adapter = categoryListItemAdapter
        rvProducts.adapter = productListItemAdapter
        ivWishList.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_wishListFragment) }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categoryListItemAdapter.setData(it) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect { productListItemAdapter.setData(it) }
            }
        }
    }
}