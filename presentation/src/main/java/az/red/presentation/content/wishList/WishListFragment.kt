package az.red.presentation.content.wishList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.wishList.adapter.WishListItemAdapter
import az.red.presentation.databinding.FragmentWishListBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class WishListFragment : BaseFragment<FragmentWishListBinding, WishListViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWishListBinding
        get() = FragmentWishListBinding::inflate

    override val kClass: KClass<WishListViewModel>
        get() = WishListViewModel::class

    private val adapter by lazy {
        WishListItemAdapter(
            removeFromWishList = { viewModel.removeFromWishList(it) },
            addToCart = { viewModel.addToCart(it) })
    }

    override val bindViews: FragmentWishListBinding.() -> Unit = {
        navigateBack()
        rvProducts.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wishList.collect {
                    adapter.setData(it.products)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    if (it) layoutLoading.root.visible()
                    else layoutLoading.root.gone()
                }
            }
        }
    }

    private fun navigateBack() {
        binding.toolbar.setNavigationOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

}