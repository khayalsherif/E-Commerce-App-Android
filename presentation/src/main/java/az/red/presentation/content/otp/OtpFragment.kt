package az.red.presentation.content.otp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import az.red.presentation.base.BaseFragment
import az.red.presentation.databinding.FragmentOtpBinding
import kotlin.reflect.KClass

class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOtpBinding
        get() = FragmentOtpBinding::inflate
    override val kClass: KClass<OtpViewModel>
        get() = OtpViewModel::class
    private lateinit var navController: NavController

    override val bindViews: FragmentOtpBinding.() -> Unit = {
        navController = findNavController()
    }

}