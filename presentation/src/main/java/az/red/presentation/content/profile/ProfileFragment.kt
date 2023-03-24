package az.red.presentation.content.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    val languages = arrayOf("US", "AZ")

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
    override val kClass: KClass<ProfileViewModel>
        get() = ProfileViewModel::class
    private lateinit var navController: NavController
    private var isDarkMode: Boolean = false

    override val bindViews: FragmentProfileBinding.() -> Unit = {
        navController = findNavController()

        spinner.adapter = ArrayAdapter(
            requireContext(), R.layout.dropdown_menu_popup_item,
            languages
        )

        cvChangeCurrentLanguage.setOnClickListener {
            spinner.performClick()
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tvCurrentLanguage.text = languages[0]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.saveCurrentLanguage(languages[position])
                tvCurrentLanguage.text = languages[position]
            }

        }

        lifecycleScope.launch {
            viewModel.isDarkMode.collect {
                isDarkMode = it
            }
        }

        cvChangeAppTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            viewModel.saveDarkMode(isDarkMode)
            changeUI()
        }

        btnGoMyProducts.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_homeFragment)
        }
        changeUI()
        navigateBack()
        logOut()

    }

    //Functions start

    private fun logOut(){
        binding.rlLogOut.setOnClickListener {
            viewModel.logOut()
            navController.navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun navigateBack() {
        binding.btnNavigateBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun changeUI() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.llNightMode.visible()
            binding.llLightMode.gone()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.llNightMode.gone()
            binding.llLightMode.visible()
        }
    }
}