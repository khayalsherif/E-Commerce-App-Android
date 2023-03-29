package az.red.presentation.content.orders

import android.view.LayoutInflater
import android.view.ViewGroup
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
import az.red.presentation.content.orders.dialog.LeaveReviewDialog
import az.red.presentation.databinding.FragmentOrdersBinding
import az.red.presentation.databinding.ItemCancelledBinding
import az.red.presentation.databinding.ItemCompletedBinding
import az.red.presentation.databinding.ItemOngoingBinding
import coil.load
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class OrdersFragment : BaseFragment<FragmentOrdersBinding, OrdersViewModel>() {

    private var onGoingCount: Int = 0
    private var completeCount: Int = 0
    private var cancelledCount: Int = 0

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersBinding
        get() = FragmentOrdersBinding::inflate
    override val kClass: KClass<OrdersViewModel>
        get() = OrdersViewModel::class


    override val bindViews: FragmentOrdersBinding.() -> Unit = {
        viewModel.getCustomerOrders()
        observeOrderValues()
        navigateBack()
        expandOrShrinkLayouts()
    }

    private fun observeOrderValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getCustomerOrderResponse.collect { networkResult ->
                        onGoingCount = 0
                        cancelledCount = 0
                        completeCount= 0

                        adapterCancelled.submitList(emptyList())
                        adapterCompleted.submitList(emptyList())
                        adapterOngoing.submitList(emptyList())

                        for (index in networkResult.indices) {

                            if (networkResult[index].canceled) {
                                cancelledCount++
                                if (binding.rvCancelled.adapter == null) {
                                    binding.rvCancelled.adapter = adapterCancelled
                                }
                                adapterCancelled.submitList(networkResult[index].products)
                            }

                            if (networkResult[index].status == "shipped" && networkResult[index].canceled.not()) {
                                completeCount++
                                if (binding.rvCompleted.adapter == null) {
                                    binding.rvCompleted.adapter = adapterCompleted
                                }
                                adapterCompleted.submitList(networkResult[index].products)
                            }

                            if (networkResult[index].status == "not shipped" && networkResult[index].canceled.not()) {
                                onGoingCount++
                                if (binding.rvOngoing.adapter == null) {
                                    binding.rvOngoing.adapter = adapterOngoing
                                }
                                adapterOngoing.submitList(networkResult[index].products)
                            }

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

    private fun expandOrShrinkLayouts() {
        binding.apply {
            flOngoing.setOnClickListener {
                viewModel.expandOngoingCard()
                val ongoingState = viewModel.ongoingExpandState.value
                if (ongoingState) {
                    if (onGoingCount > 0) rvOngoing.visible() else llEmptyImageOngoing.visible()
                    icRightOngoing.setImageResource(R.drawable.ic_down)

                } else {
                    icRightOngoing.setImageResource(R.drawable.ic_right)
                    llEmptyImageOngoing.gone()
                    rvOngoing.gone()

                }
            }

            flCompleted.setOnClickListener {
                viewModel.expandCompleteCard()
                val completedExpandState = viewModel.completedExpandState.value
                if (completedExpandState) {
                    if (completeCount > 0) rvCompleted.visible() else llEmptyImageCompleted.visible()
                    icRightCompleted.setImageResource(R.drawable.ic_down)

                } else {
                    icRightCompleted.setImageResource(R.drawable.ic_right)
                    llEmptyImageCompleted.gone()
                    rvCompleted.gone()
                }
            }

            flCancelled.setOnClickListener {
                viewModel.expandCancelledCard()
                val cancelledExpandState = viewModel.cancelledExpandState.value
                if (cancelledExpandState) {
                    if (cancelledCount > 0) rvCancelled.visible() else llEmptyImageCancelled.visible()
                    icRightCancelled.setImageResource(R.drawable.ic_down)

                } else {
                    icRightCancelled.setImageResource(R.drawable.ic_right)
                    llEmptyImageCancelled.gone()
                    rvCancelled.gone()
                }
            }
        }
    }

    private val adapterOngoing by lazy {
        RecyclerListAdapter<ItemOngoingBinding, CartProduct>(
            onInflate = ItemOngoingBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    tvOngoingProductName.text = data.product.name
                    tvOngoingProductPrice.text = "US $ ${data.product.currentPrice}"
                    imageOngoing.load(data.product.imageUrls.first())
                }
            }
        )
    }

    private val adapterCompleted by lazy {
        RecyclerListAdapter<ItemCompletedBinding, CartProduct>(
            onInflate = ItemCompletedBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    tvCompletedProductName.text = data.product.name
                    tvCompletedProductPrice.text = "US $ ${data.product.currentPrice}"
                    imageOngoing.load(data.product.imageUrls.first())
                    btnLeaveReview.setOnClickListener {
                        val leaveReviewDialog = LeaveReviewDialog(data)
                        leaveReviewDialog.show(requireActivity().supportFragmentManager,leaveReviewDialog.tag)
                    }
                }
            }
        )
    }

    private val adapterCancelled by lazy {
        RecyclerListAdapter<ItemCancelledBinding, CartProduct>(
            onInflate = ItemCancelledBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {

                    tvCancelledProductName.text = data.product.name
                    tvCancelledProductPrice.text = "US $ ${data.product.currentPrice}"
                    imageOngoing.load(data.product.imageUrls.first())
                }
            }
        )
    }

    private fun navigateBack() {
        binding.ordersToolbar.setNavigationOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }
}