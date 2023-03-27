package az.red.presentation.content.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import az.red.domain.common.NetworkResult
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentProductDetailBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate
    override val kClass: KClass<ProductDetailViewModel>
        get() = ProductDetailViewModel::class
    private lateinit var navController: NavController

    private val args by navArgs<ProductDetailFragmentArgs>()

    override val bindViews: FragmentProductDetailBinding.() -> Unit = {
        navController = findNavController()
        toolbar.setNavigationOnClickListener { navController.popBackStack() }
        viewModel.getProductDetail(args.id)


        lifecycleScope.launch {
            viewModel.productResponse.collect {
                when (it) {
                    is NetworkResult.Empty -> {}
                    is NetworkResult.Error -> {
                        showToast(it.message!!)
                    }
                    is NetworkResult.Exception -> {
                        showToast(it.message!!)
                    }
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        val list = it.data!!.imageUrls.map { image -> SlideModel(image ) }
                        imageSlider.setImageList(list,ScaleTypes.FIT)
                    }
                }
            }
        }


    }
}