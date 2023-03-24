package az.red.presentation.content.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentCartBinding
import kotlin.reflect.KClass

class CartFragment : BaseFragment<FragmentCartBinding,CartViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate
    override val kClass: KClass<CartViewModel>
        get() = CartViewModel::class

    override val bindViews: FragmentCartBinding.() -> Unit = {

    }
}