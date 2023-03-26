package az.red.presentation.content.cart

import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.order.request.DeliveryAddress
import az.red.domain.model.order.request.OrderRequest
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.base.RecyclerListAdapter
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.databinding.CartListItemBinding
import az.red.presentation.databinding.FragmentCartBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass
import coil.load
import com.google.android.material.snackbar.Snackbar

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {

    private var totalSum: Double = 0.0
    private lateinit var customerId: String
    private lateinit var products : List<CartProduct>

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
    }

    private fun observeCartValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cartResponse.collect {
                        binding.layoutLoading.root.gone()
                        if (binding.rvCart.adapter == null) {
                            binding.rvCart.adapter = adapter
                        } else {
                            adapter.submitList(it.data!!.products)
                            customerId = it.data!!.customerId._id.toString()
                            products = it.data!!.products
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect {
                        binding.layoutLoading.root.visible()
                    }
                }
            }
        }
    }

    private fun createOrder() {
        binding.btnCheckout.setOnClickListener {
            val orderRequest = OrderRequest(
                "63e77ae212c7cfc9d421fddd",
                DeliveryAddress("Azerbaijan","M.Hadi 256","Baku","1024"),
                "Baku AZ1000",
                "Credit card",
                "not shipped",
                "fsaliyeva96@gmail.com",
                "+380630000000",
                "Thank you for order! You are welcome!",
                "<h1>Your order is placed. OrderNo is ###.</h1><p>Have a good day!</p>",
                products
            )

            viewModel.createOrder(orderRequest)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.createOrderResponse.collect {
                        Snackbar.make(requireView(), "Success", Snackbar.LENGTH_LONG).show()
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
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteCart()
            lifecycleScope.launch {
                binding.layoutLoading.root.gone()
                viewModel.deleteCartResponse.collect { result ->
                    showToast(result.data!!.message)
                }
            }
        }

        builder.setNegativeButton(android.R.string.no) { dialog, _ ->
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


}