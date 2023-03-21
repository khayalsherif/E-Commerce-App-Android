package az.red.presentation.content.login

import android.view.LayoutInflater
import android.view.ViewGroup
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentLoginBinding
import kotlin.reflect.KClass

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    override val kClass: KClass<LoginViewModel>
        get() = LoginViewModel::class

    override val bindViews: FragmentLoginBinding.() -> Unit = {
        viewModel.login()
    }
}