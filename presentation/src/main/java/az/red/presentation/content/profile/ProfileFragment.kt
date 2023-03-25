package az.red.presentation.content.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import az.red.presentation.R
import az.red.presentation.base.BaseFragment
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.MainActivity
import az.red.presentation.databinding.FragmentProfileBinding
import kotlin.reflect.KClass

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),
    AdapterView.OnItemSelectedListener {

    private val languages = arrayOf("US", "AZ")

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
    override val kClass: KClass<ProfileViewModel>
        get() = ProfileViewModel::class
    private lateinit var navController: NavController

    private var isDarkMode: Boolean = false

    override val bindViews: FragmentProfileBinding.() -> Unit = {
        navController = findNavController()

        isDarkMode = viewModel.isDarkMode

        binding.tvCurrentLanguage.text = viewModel.getCurrentLanguage().uppercase()
        spinner.adapter = ArrayAdapter(
            requireContext(), R.layout.dropdown_menu_popup_item,
            languages
        )

        cvChangeCurrentLanguage.setOnClickListener {
            spinner.onItemSelectedListener = this@ProfileFragment
            spinner.performClick()
        }

        cvChangeAppTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            viewModel.saveDarkMode(isDarkMode)
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        btnGoMyProducts.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_homeFragment)
        }

        changeUI()
        navigateBack()
        logOut()
    }

    private fun logOut() {
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.saveCurrentLanguage(languages[position].lowercase())
        (requireActivity() as MainActivity).recreate()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}