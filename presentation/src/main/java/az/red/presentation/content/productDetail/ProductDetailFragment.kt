package az.red.presentation.content.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentProductDetailBinding
import kotlin.reflect.KClass

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate
    override val kClass: KClass<ProductDetailViewModel>
        get() = ProductDetailViewModel::class

    private val args by navArgs<ProductDetailFragmentArgs>()

    override val bindViews: FragmentProductDetailBinding.() -> Unit = {

        println(args.id)

    }
}