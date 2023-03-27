package az.red.presentation.content.cart

import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import az.red.domain.model.cart.CartProduct
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.base.RecyclerListAdapter
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.MainActivity
import az.red.presentation.databinding.CartListItemBinding
import az.red.presentation.databinding.FragmentCartBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass
import coil.load
import com.google.android.material.snackbar.Snackbar

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private var totalSum: Double = 0.0
    private lateinit var products: List<CartProduct>

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate
    override val kClass: KClass<CartViewModel>
        get() = CartViewModel::class

    override val bindViews: FragmentCartBinding.() -> Unit = {
        viewModel.getCart()
        observeCartValues()
        checkoutValue()
        deleteCart()
        createOrder()
        navigateBack()
    }

    private fun observeCartValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cartResponse.collect { networkResult ->
                        binding.layoutLoading.root.gone()
                        if (binding.rvCart.adapter == null) {
                            binding.rvCart.adapter = adapter
                        } else {
                            adapter.submitList(networkResult.data!!.products)
                            products = networkResult.data!!.products
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect {
                        if (it)
                            binding.layoutLoading.root.visible()
                        else
                            binding.layoutLoading.root.gone()
                    }
                }
            }
        }
    }

    private fun createOrder() {
        binding.btnCheckout.setOnClickListener {

            viewModel.createOrder(products)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.createOrderResponse.collect {//TODO: Add when for network result. In Snackbar display which items have problems (from error response product availability details)
                        Snackbar.make(
                            requireView(),
                            it.data?.message.toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun deleteCart() {
        binding.cartMenuDelete.setOnClickListener {
            showDeleteCartAlert()
        }
    }

    private fun showDeleteCartAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.warning))
        builder.setMessage(getString(R.string.do_you_want_delete_cart))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.deleteCart()
            lifecycleScope.launch {
                binding.layoutLoading.root.gone()
                viewModel.deleteCartResponse.collect { _ ->
                    Snackbar.make(
                        requireView(),
                        "Cart deleted successfully!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    (activity as MainActivity).recreate()
                }
            }
        }

        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun checkoutValue() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.checkoutValue.collect { checkoutValue ->
                        if (checkoutValue.toDouble() > 0.0) {
                            binding.apply {
                                cvCheckoutContainer.visible()
                                cartMenuDelete.isClickable = true
                                cartMenuDelete.setColorFilter(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.carrot_accent
                                    ), android.graphics.PorterDuff.Mode.SRC_IN
                                )
                            }
                        } else {
                            binding.apply {
                                cvCheckoutContainer.gone()
                                cartMenuDelete.isClickable = false
                                cartMenuDelete.setColorFilter(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.dark_line_color
                                    ), android.graphics.PorterDuff.Mode.SRC_IN
                                )
                            }
                        }
                        binding.txtTotalSumValue.text = checkoutValue
                    }
                }
            }
        }
    }

    private val adapter by lazy {
        RecyclerListAdapter<CartListItemBinding, CartProduct>(
            onInflate = CartListItemBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    var cartQuantity = data.cartQuantity

                    //Cart texts
                    txtCartProductName.text = data.product.name
                    txtCartProductPrice.text = "$ ${data.product.currentPrice}"
                    txtCartProductQuantity.text = cartQuantity.toString()

                    //Cart image
                    if (data.product.imageUrls.firstOrNull().isNullOrEmpty()) {
                        cartImage.setImageResource(R.drawable.ic_launcher_foreground)
                    } else {
                        cartImage.load(data.product.imageUrls.first())
                    }

                    //Increase cart quantity
                    cartProductPlus.setOnClickListener {
                        cartQuantity += 1
                        txtCartProductQuantity.text = cartQuantity.toString()
                        if (cbCart.isChecked) {
                            totalSum += data.product.currentPrice
                            viewModel.checkoutValue.value = totalSum.toString()
                        }
                    }

                    //Decrease cart quantity
                    cartProductMinus.setOnClickListener {
                        if (cartQuantity > 1) {
                            cartQuantity -= 1
                            txtCartProductQuantity.text = cartQuantity.toString()
                        }
                        if (cbCart.isChecked) {
                            totalSum -= data.product.currentPrice
                            viewModel.checkoutValue.value = totalSum.toString()
                        }
                    }

                    //Cart checkbox
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

    override fun onPause() {
        viewModel.checkoutValue.value = "0.0"
        super.onPause()
    }

    private fun navigateBack() {
        binding.toolbar.setNavigationOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

}