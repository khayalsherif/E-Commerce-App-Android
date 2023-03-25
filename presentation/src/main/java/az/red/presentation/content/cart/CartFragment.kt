package az.red.presentation.content.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import az.red.domain.model.cart.CartProduct
import az.red.presentation.base.BaseFragment
import az.red.presentation.base.RecyclerListAdapter
import az.red.presentation.databinding.CartListItemBinding
import az.red.presentation.databinding.FragmentCartBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private var totalSum: Double = 0.0
    private var cartQuantity: Int = 0

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate
    override val kClass: KClass<CartViewModel>
        get() = CartViewModel::class

    override val bindViews: FragmentCartBinding.() -> Unit = {
        viewModel.getCart()
        observeValues()
        checkoutValue()
    }

    private fun observeValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cartResponse.collect {
                        if (binding.rvCart.adapter == null) {
                            binding.rvCart.adapter = adapter
                        } else adapter.submitList(it.data!!.products)
                    }
                }

            }
        }
    }

    private fun checkoutValue() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.checkoutValue.collect{
                        binding.txtTotalSumValue.text = it
                    }
                }
            }
        }

    }

    private val adapter by lazy {
        RecyclerListAdapter<CartListItemBinding, CartProduct>(
            onInflate = CartListItemBinding::inflate,
            onBind = { binding, data, position ->
                binding.apply {
                    var cartQuantity = data.cartQuantity

                    cartProductPlus.setOnClickListener {
                        cartQuantity += 1
                        txtCartProductQuantity.text = cartQuantity.toString()
                    }

                    cartProductMinus.setOnClickListener {
                        cartQuantity -= 1
                        txtCartProductQuantity.text = cartQuantity.toString()
                    }
                    txtCartProductName.text = data.product.name
                    txtCartProductPrice.text = "$ ${data.product.currentPrice}"
                    txtCartProductQuantity.text = cartQuantity.toString()
                    Glide.with(requireContext()).load(data.product.imageUrls[0]).into(cartImage)
                    cbCart.setOnCheckedChangeListener { _, isChecked ->
                        val sum = cartQuantity * data.product.currentPrice
                        if (isChecked) {
                            totalSum += sum
                        } else {
                            totalSum -= sum
                        }
                        viewModel.checkoutValue.value = totalSum.toString()
                    }
                }
            }
        )
    }


}