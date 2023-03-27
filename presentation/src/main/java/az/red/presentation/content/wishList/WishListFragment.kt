package az.red.presentation.content.wishList

import android.view.LayoutInflater
import android.view.ViewGroup
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentWishListBinding
import kotlin.reflect.KClass


class WishListFragment : BaseFragment<FragmentWishListBinding, WishListViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWishListBinding
        get() = FragmentWishListBinding::inflate

    override val kClass: KClass<WishListViewModel>
        get() = WishListViewModel::class


}