package az.red.presentation.content

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.presentation.R
import az.red.presentation.common.gone
import az.red.presentation.common.visible
import az.red.presentation.databinding.ActivityMainBinding
import az.red.presentation.util.LocalLanguageManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val useCase by inject<SessionManagerUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocalLanguageManager().onAttach(base!!, useCase))
    }
}