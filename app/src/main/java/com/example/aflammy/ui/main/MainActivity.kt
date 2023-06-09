package com.example.aflammy.ui.main

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aflammy.R
import com.example.aflammy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        setTheme(R.style.Theme_Aflammy)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        installSplashScreen()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.exploringFragment,
                R.id.myListFragment,
                R.id.profileFragment,
            )
        )
        val navController = findNavController(R.id.nav_host_fragment)
        binding.mainBottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setBottomNavigationVisibility(navController)
        setNavigationController(navController)
    }

    private fun setBottomNavigationVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.mainBottomNavigation.isVisible = destination.id != R.id.loginFragment
        }
    }

    private fun setNavigationController(navController: NavController) {
        binding.mainBottomNavigation.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            navController.popBackStack(item.itemId, inclusive = false)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}