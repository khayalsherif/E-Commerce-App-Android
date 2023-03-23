package az.red.presentation.content.register

import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import az.red.data.model.auth.register.RegisterRequest
import az.red.domain.common.NetworkResult
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.setDrawableRightTouch
import az.red.presentation.common.visible
import az.red.presentation.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {
    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate
    override val kClass: KClass<RegisterViewModel>
        get() = RegisterViewModel::class
    private lateinit var navController: NavController

    private var passwordIsVisible = false


    override val bindViews: FragmentRegisterBinding.() -> Unit = {
        navController = findNavController()

        // Text Watchers
        inputEmail.addTextChangedListener(textWatcher)
        inputPassword.addTextChangedListener(textWatcher)
        inputFirstName.addTextChangedListener(textWatcher)
        inputLastName.addTextChangedListener(textWatcher)
        inputUserName.addTextChangedListener(textWatcher)

        // On Clicks
        toolbar.setNavigationOnClickListener { navController.popBackStack() }
        buttonSignIn.setOnClickListener { navController.popBackStack() }

        inputPassword.setDrawableRightTouch {
            passwordVisibility(binding.inputPassword, passwordIsVisible)
            passwordIsVisible = !passwordIsVisible
        }

        buttonContinue.setOnClickListener {
            val request = RegisterRequest(
                firstName = inputFirstName.text.toString(),
                lastName = inputLastName.text.toString(),
                login = inputUserName.text.toString(),
                email = inputEmail.text.toString(),
                password = inputPassword.text.toString(),
                isAdmin = false
            )
            viewModel.register(request)
        }

        lifecycleScope.launch {
            viewModel.registerResponse.collect {
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
                        //navController.navigate(R.id.action_registerFragment_to_otpFragment)
                        println(it.data!!)
                    }
                }
            }
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.buttonContinue.isEnabled = binding.inputEmail.text.isNotEmpty() &&
                    binding.inputPassword.text.isNotEmpty() &&
                    binding.inputFirstName.text.isNotEmpty() &&
                    binding.inputLastName.text.isNotEmpty() &&
                    binding.inputUserName.text.isNotEmpty()
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