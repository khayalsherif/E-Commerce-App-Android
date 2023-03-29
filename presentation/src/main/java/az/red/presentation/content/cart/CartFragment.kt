package az.red.presentation.content.cart

import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import az.red.domain.common.NetworkResult
import az.red.domain.model.cart.Cart
import az.red.domain.model.cart.CartProduct
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

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate
    override val kClass: KClass<CartViewModel>
        get() = CartViewModel::class

    override val bindViews: FragmentCartBinding.() -> Unit = {

        observeCartValues()
        checkoutValue()
        deleteSelectedCartItems()
        createOrder()
        navigateBack()
    }

    private fun observeCartValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cart.collect {
                        if (binding.rvCart.adapter == null) {
                            binding.rvCart.adapter = adapter
                        }
                        adapter.submitList(it.products)
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
            viewModel.createOrder()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createOrderResponse.collect {
                    if (it is NetworkResult.Success) {
                        if (it.data?.message.isNullOrEmpty().not()) {
                            Snackbar.make(
                                requireView(),
                                (it.data!!.message + it.data!!.productAvailibilityInfo?.unavailableProducts?.joinToString(
                                    ", ",
                                    ": "
                                ) { p -> p.name + " (available quantity: ${it.data!!.productAvailibilityInfo!!.productsAvailibilityDetails.first { d -> d.productId == p._id }.realQuantity})" }),
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else
                            Snackbar.make(
                                requireView(),
                                "Order has been created successfully",
                                Snackbar.LENGTH_LONG
                            ).show()
                    }
                }
            }
        }
    }

    private fun deleteSelectedCartItems() {
        binding.cartMenuDelete.setOnClickListener {
            showDeleteCartAlert()
        }
    }

    private fun showDeleteCartAlert() {
        lifecycleScope.launch {
            binding.layoutLoading.root.gone()
            viewModel.deleteCartResponse.collect { _ ->
                Snackbar.make(
                    requireView(),
                    "Cart item(s) deleted successfully!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.warning))
        builder.setMessage(getString(R.string.do_you_want_delete_cart))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.deleteSelectedCartItems()
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
                    viewModel.cart.collect { cart ->

                        val checkoutValue = if (cart == Cart.NULL) 0.0 else
                            cart.products.filter { it.isSelected }
                                .sumOf { it.product.currentPrice * it.cartQuantity }
                        if (checkoutValue > 0.0) {
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
                        binding.txtTotalSumValue.text = checkoutValue.toString()
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
                    //Cart texts
                    txtCartProductName.text = data.product.name
                    txtCartProductPrice.text = "$ ${data.product.currentPrice}"
                    txtCartProductQuantity.text = data.cartQuantity.toString()
                    cbCart.isChecked = data.isSelected

                    //Cart image
                    if (data.product.imageUrls.firstOrNull().isNullOrEmpty()) {
                        cartImage.setImageResource(R.drawable.ic_launcher_foreground)
                    } else {
                        cartImage.load(data.product.imageUrls.first())
                    }

                    //Increase cart quantity
                    cartProductPlus.setOnClickListener {

                        viewModel.incrementProductQty(data._id)
                    }

                    //Decrease cart quantity
                    cartProductMinus.setOnClickListener {
                        viewModel.decrementProductQty(data._id)
                    }

                    //Cart checkbox
                    cbCart.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked != data.isSelected)
                            viewModel.setCartProductCheck(data._id, isChecked)
                    }
                }
            }
        )
    }

    private fun navigateBack() {
        binding.toolbar.setNavigationOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

}