package az.red.presentation.content.productDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import az.red.domain.common.NetworkResult
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.home.adapter.ProductListItemAdapter
import az.red.presentation.databinding.FragmentProductDetailBinding
import az.red.presentation.util.ClickListener
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(),
    ClickListener {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate
    override val kClass: KClass<ProductDetailViewModel>
        get() = ProductDetailViewModel::class
    private lateinit var navController: NavController

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val productListItemAdapter by lazy {
        ProductListItemAdapter(
            addToWishList = { showToast("Added to wishlist") },
            clickListener = this@ProductDetailFragment
        )
    }

    @SuppressLint("SetTextI18n")
    override val bindViews: FragmentProductDetailBinding.() -> Unit = {
        init()
        integrationRcView()

        lifecycleScope.launch {
            viewModel.productResponse.collect {
                when (it) {
                    is NetworkResult.Empty -> {}
                    is NetworkResult.Error -> {
                        showToast(it.message!!)
                        layoutLoading.root.gone()
                    }
                    is NetworkResult.Exception -> {
                        showToast(it.message!!)
                        layoutLoading.root.gone()
                    }
                    is NetworkResult.Loading -> {
                        layoutLoading.root.visible()
                    }
                    is NetworkResult.Success -> {
                        layoutLoading.root.gone()
                        viewModel.getSimilarProducts(it.data!!.categories)
                        val list = it.data!!.imageUrls.map { image -> SlideModel(image) }
                        imageSlider.setImageList(list, ScaleTypes.FIT)
                        textTitle.text = it.data?.name ?: ""
                        textDescription.text = it.data?.description ?: ""
                        textNewPrice.text = "US $${it.data?.currentPrice}"
                        textOldPrice.text = "US $${it.data?.currentPrice}"
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.similarProductResponse.collect {
                when (it) {
                    is NetworkResult.Empty -> {}
                    is NetworkResult.Error -> {
                        showToast(it.message!!)
                        layoutLoading.root.visible()
                    }
                    is NetworkResult.Exception -> {
                        showToast(it.message!!)
                        layoutLoading.root.visible()
                    }
                    is NetworkResult.Loading -> {
                        layoutLoading.root.gone()
                    }
                    is NetworkResult.Success -> {
                        productListItemAdapter.setData(it.data!!)
                    }
                }
            }
        }

    }

    private fun init() {
        navController = findNavController()
        binding.toolbar.setNavigationOnClickListener { navController.popBackStack() }
        viewModel.getProductDetail(args.id)
    }

    private fun integrationRcView() {
        binding.rcViewSimilarGoods.adapter = productListItemAdapter
        binding.rcViewSimilarGoods.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onClick(view: View, id: String) {
        navController.navigate(
            ProductDetailFragmentDirections.actionProductDetailFragmentSelf(id = id)
        )
    }
}