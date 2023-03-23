package az.red.presentation.content.home

import android.view.LayoutInflater
import android.view.ViewGroup
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val bindingCallBack =
        { inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean ->
            FragmentHomeBinding.inflate(
                inflater,
                parent,
                attachToParent
            )
        }
    override val kClass = HomeViewModel::class

    override val bindViews: FragmentHomeBinding.() -> Unit = { }

}