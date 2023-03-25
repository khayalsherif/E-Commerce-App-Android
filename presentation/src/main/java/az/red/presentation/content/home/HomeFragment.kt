package az.red.presentation.content.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import az.red.presentation.base.BaseFragment
import az.red.presentation.content.home.adapter.CategoryListItemAdapter
import az.red.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.activity.OnBackPressedCallback



class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private lateinit var adapter: CategoryListItemAdapter
    override val bindingCallBack =
        { inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean ->
            FragmentHomeBinding.inflate(
                inflater,
                parent,
                attachToParent
            )
        }
    override val kClass = HomeViewModel::class


    override val bindViews: FragmentHomeBinding.() -> Unit = {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
        adapter = CategoryListItemAdapter()
        rvSubCategoryCards.apply {
            adapter = adapter
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { adapter.setData(it) }
            }
        }
    }
}