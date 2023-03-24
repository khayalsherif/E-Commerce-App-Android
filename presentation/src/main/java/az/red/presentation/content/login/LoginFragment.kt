package az.red.presentation.content.login

import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import az.red.domain.common.NetworkResult
import az.red.domain.model.auth.login.LoginRequest
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.setDrawableRightTouch
import az.red.presentation.common.visible
import az.red.presentation.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    override val kClass: KClass<LoginViewModel>
        get() = LoginViewModel::class
    private lateinit var navController: NavController

    private var passwordIsVisible = false

    override val bindViews: FragmentLoginBinding.() -> Unit = {
        navController = findNavController()

        inputName.addTextChangedListener(textWatcher)
        inputPassword.addTextChangedListener(textWatcher)

        inputPassword.setDrawableRightTouch {
            passwordVisibility(binding.inputPassword, passwordIsVisible)
            passwordIsVisible = !passwordIsVisible
        }

        buttonSignIn.setOnClickListener {
            val request = LoginRequest(inputName.text.toString(), inputPassword.text.toString())
            viewModel.login(request)
        }

        binding.buttonSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        lifecycleScope.launch {
            viewModel.loginResponse.collect {
                when (it) {
                    is NetworkResult.Empty -> {
                    }
                    is NetworkResult.Error -> {
                        layoutLoading.root.gone()
                        showToast(it.message!!)
                    }
                    is NetworkResult.Exception -> {
                        layoutLoading.root.gone()
                        showToast(it.message!!)
                    }
                    is NetworkResult.Loading -> {
                        layoutLoading.root.visible()
                    }
                    is NetworkResult.Success -> {
                        layoutLoading.root.gone()
                        viewModel.saveToken(it.data!!.token!!, binding.checkBox.isChecked)

                        navController.navigate( R.id.action_loginFragment_to_homeFragment )
                    }
                }
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.buttonSignIn.isEnabled =
                binding.inputName.text.isNotEmpty() && binding.inputPassword.text.isNotEmpty()
        }
    }

    private fun passwordVisibility(editText: EditText, isVisible: Boolean) {
        if (isVisible) {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password,
                0,
                R.drawable.ic_open_eye,
                0
            )
            editText.transformationMethod = PasswordTransformationMethod()
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password,
                0,
                R.drawable.ic_close_eye,
                0
            )
            editText.transformationMethod = null
        }
    }
}