package az.red.presentation.content.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import az.red.presentation.base.BaseFragment
import az.red.presentation.content.login.LoginViewModel
import az.red.presentation.databinding.FragmentWelcomeBinding
import kotlin.reflect.KClass

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, LoginViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding
        get() = FragmentWelcomeBinding::inflate
    override val kClass: KClass<LoginViewModel>
        get() = LoginViewModel::class

    private lateinit var navController: NavController

    override val bindViews: FragmentWelcomeBinding.() -> Unit = {
        navController = findNavController()
    }
}