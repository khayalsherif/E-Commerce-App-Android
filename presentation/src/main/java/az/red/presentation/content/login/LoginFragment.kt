package az.red.presentation.content.login

import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.setDrawableRightTouch
import az.red.presentation.databinding.FragmentLoginBinding
import kotlin.reflect.KClass

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    override val kClass: KClass<LoginViewModel>
        get() = LoginViewModel::class

    private var passwordIsVisible = false

    override val bindViews: FragmentLoginBinding.() -> Unit = {

        binding.inputPassword.setDrawableRightTouch {
            passwordVisibility(binding.inputPassword, passwordIsVisible)
            passwordIsVisible = !passwordIsVisible
        }
    }


    private fun passwordVisibility(editText: EditText, isVisible: Boolean) {
        if (isVisible) {
            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_open_eye, 0)
            editText.transformationMethod = PasswordTransformationMethod()
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_close_eye, 0)
            editText.transformationMethod = null
        }
    }
}