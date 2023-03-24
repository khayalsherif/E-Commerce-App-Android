package az.red.presentation.content

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import az.red.presentation.R
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.content.profile.ProfileViewModel
import az.red.presentation.databinding.ActivityMainBinding
import az.red.presentation.util.LocalLanguageManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProfileViewModel by viewModel()
    private var isDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.isDarkMode.collect {
                isDarkMode = it
            }
        }

        checkAppThemeMode()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.ordersFragment, R.id.cartFragment, R.id.profileFragment -> {
                    binding.bottomNavigationView.visible()
                }
                else -> {
                    binding.bottomNavigationView.gone()
                }
            }
        }
    }

    private fun checkAppThemeMode() {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocalLanguageManager().onAttach(base!!))
    }
}