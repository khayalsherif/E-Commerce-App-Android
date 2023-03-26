package az.red.presentation.content.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.home.adapter.CategoryListItemAdapter
import az.red.presentation.content.home.adapter.ProductListItemAdapter
import az.red.presentation.content.home.adapter.ProductListItemPagingAdapter
import az.red.presentation.content.home.enums.ViewOption
import az.red.presentation.databinding.FragmentHomeBinding
import az.red.presentation.util.ClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), ClickListener {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    override val kClass: KClass<HomeViewModel>
        get() = HomeViewModel::class

    private lateinit var navController: NavController

    private val categoryListItemAdapter by lazy {
        CategoryListItemAdapter {
            viewModel.selectCategory(it)
        }
    }

    private val productListItemAdapter by lazy {
        ProductListItemAdapter(
            addToWishList = { showToast("added to wishlist") },
            clickListener = this@HomeFragment
        )
    }
    private val paginatedProductListItemAdapter by lazy {
        ProductListItemPagingAdapter(
            addToWishList = { showToast("added to wishlist") },
            clickListener = this@HomeFragment
        )
    }

    override val bindViews: FragmentHomeBinding.() -> Unit = {
        navController = findNavController()

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
        binding.rvProductListPaging.adapter = paginatedProductListItemAdapter

        ivWishList.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_wishListFragment) }

        tvSeeAll.setOnClickListener {
            viewModel.viewOption.value = ViewOption.BLOCK
        }

        ivFilter.setOnClickListener {
            viewModel.viewOption.value = ViewOption.FILTER
        }

        ivLines.setOnClickListener {
            viewModel.viewOption.value = ViewOption.LIST
        }

        ivBlocks.setOnClickListener {
            viewModel.viewOption.value = ViewOption.BLOCK
        }

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
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                paginatedProductListItemAdapter.submitData(it)
                println(it.map { i -> i.name })
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewOption.collect {
                    when (it) {
                        ViewOption.FILTER -> {
                            tvSeeAll.visible()
                            categoryFilterSection.visible()
                            rvProducts.visible()
                            productDisplaySettings.gone()
                            rvProductListPaging.gone()
                        }
                        ViewOption.BLOCK -> {
                            tvSeeAll.gone()
                            rvProducts.gone()
                            productDisplaySettings.visible()
                            ivBlocks.gone()
                            categoryFilterSection.gone()
                            ivLines.visible()
                            rvProductListPaging.visible()
                            rvProductListPaging.layoutManager =
                                GridLayoutManager(requireContext(), 2, VERTICAL, false)
                            paginatedProductListItemAdapter.switchToBlockView()
                        }
                        ViewOption.LIST -> {
                            tvSeeAll.gone()
                            rvProducts.gone()
                            productDisplaySettings.visible()
                            ivBlocks.visible()
                            categoryFilterSection.gone()
                            ivLines.gone()
                            rvProductListPaging.visible()
                            rvProductListPaging.layoutManager =
                                LinearLayoutManager(requireContext())
                            paginatedProductListItemAdapter.switchToLineView()
                        }
                    }
                }
            }
        }
    }

    override fun onClick(view: View, id: String) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id = id)
        )
    }

}