package az.red.presentation.content.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import az.red.domain.model.cart.CartProduct
import az.red.domain.model.order.response.GetCustomerOrder
import az.red.domain.model.product.Product
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.base.RecyclerListAdapter
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.databinding.CartListItemBinding
import az.red.presentation.databinding.FragmentOrdersBinding
import az.red.presentation.databinding.ItemCancelledBinding
import az.red.presentation.databinding.ItemCompletedBinding
import az.red.presentation.databinding.ItemOngoingBinding
import coil.load
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class OrdersFragment : BaseFragment<FragmentOrdersBinding,OrdersViewModel>() {

    private var onGoingExpandState : Boolean = false
    private var completedExpandState : Boolean = false
    private var cancelledExpandState : Boolean = false
    private var onGoingCount : Int = 0
    private var completeCount : Int = 0
    private var cancelledCount : Int = 0

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrdersBinding
        get() = FragmentOrdersBinding::inflate
    override val kClass: KClass<OrdersViewModel>
        get() = OrdersViewModel::class

    override val bindViews: FragmentOrdersBinding.() -> Unit = {

        observeOrderValues()
        navigateBack()
        expandOrShrinkLayouts()
    }

    private fun observeOrderValues() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getCustomerOrderResponse.collect {networkResult ->
                        for(index in networkResult.indices){

                            if(networkResult[index].canceled){
                                cancelledCount++
                                if(binding.rvCancelled.adapter == null){
                                    binding.rvCancelled.adapter = adapterCancelled
                                }
                                adapterCancelled.submitList(networkResult[index].products)
                            }

                            if(networkResult[index].status == "shipped" && networkResult[index].canceled.not()){
                                completeCount++
                                if(binding.rvCompleted.adapter == null){
                                    binding.rvCompleted.adapter = adapterCompleted
                                }
                                adapterCompleted.submitList(networkResult[index].products)
                            }

                            if(networkResult[index].status == "not shipped" && networkResult[index].canceled.not()){
                                onGoingCount++
                                if(binding.rvOngoing.adapter == null){
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

    private fun expandOrShrinkLayouts(){
        binding.apply {
            flOngoing.setOnClickListener {
                onGoingExpandState = !onGoingExpandState
                if(onGoingExpandState){
                    if(onGoingCount>0) rvOngoing.visible() else llEmptyImageOngoing.visible()
                    icRightOngoing.setImageResource(R.drawable.ic_down)

                }else{
                    icRightOngoing.setImageResource(R.drawable.ic_right)
                    llEmptyImageOngoing.gone()
                    rvOngoing.gone()
                }
            }

            flCompleted.setOnClickListener {
                completedExpandState = !completedExpandState
                if(completedExpandState){
                    if(completeCount>0) rvCompleted.visible() else llEmptyImageCompleted.visible()
                    icRightCompleted.setImageResource(R.drawable.ic_down)

                }else{
                    icRightCompleted.setImageResource(R.drawable.ic_right)
                    llEmptyImageCompleted.gone()
                    rvCompleted.gone()
                }
            }

            flCancelled.setOnClickListener {
                cancelledExpandState = !cancelledExpandState
                if(cancelledExpandState){
                    if(cancelledCount>0) rvCancelled.visible() else llEmptyImageCancelled.visible()
                    icRightCancelled.setImageResource(R.drawable.ic_down)

                }else{
                    icRightCancelled.setImageResource(R.drawable.ic_right)
                    llEmptyImageCancelled.gone()
                    rvCancelled.gone()
                }
            }
        }
    }

    private val adapterOngoing by lazy {
        RecyclerListAdapter<ItemOngoingBinding, Product>(
            onInflate = ItemOngoingBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    tvOngoingProductName.text = data.name
                    tvOngoingProductPrice.text = "US $ ${data.currentPrice}"
//                    imageOngoing.load(data.imageUrls.first())
                }
            }
        )
    }

    private val adapterCompleted by lazy {
        RecyclerListAdapter<ItemCompletedBinding, Product>(
            onInflate = ItemCompletedBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {
                    tvCompletedProductName.text = data.name
                    tvCompletedProductPrice.text = "US $ ${data.currentPrice}"
//                    imageOngoing.load(data.imageUrls.first())
                }
            }
        )
    }

    private val adapterCancelled by lazy {
        RecyclerListAdapter<ItemCancelledBinding, Product>(
            onInflate = ItemCancelledBinding::inflate,
            onBind = { binding, data, _ ->
                binding.apply {

                    tvCancelledProductName.text = data.name
                    tvCancelledProductPrice.text = "US $ ${data.currentPrice}"
//                    imageOngoing.load(data.imageUrls.first())
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