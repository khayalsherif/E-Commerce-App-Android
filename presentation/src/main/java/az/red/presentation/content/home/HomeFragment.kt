package az.red.presentation.content.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import az.red.presentation.base.BaseFragment
import az.red.presentation.content.home.adapter.CategoryListItemAdapter
import az.red.presentation.content.home.adapter.ProductListItemAdapter
import az.red.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val categoryListItemAdapter by lazy {
        CategoryListItemAdapter {
            viewModel.selectCategory(it)
        }
    }
    private val productListItemAdapter by lazy {
        ProductListItemAdapter { /*TODO:Add to wih list*/
            Toast.makeText(requireContext(), "added to wishlist", Toast.LENGTH_SHORT).show()
        }
    }
    override val bindingCallBack =
        { inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean ->
            FragmentHomeBinding.inflate(
                inflater,
                parent,
                attachToParent
            )
        }
    override val kClass = HomeViewModel::class


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