package com.example.xmlrealestate.ui.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.xmlrealestate.R
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configSplashScreen()
        configBinding()
        configBottomNav()
        requestLocationPermission()
    }

    //This method is used to show the splash screen for 1 second.
    private fun configSplashScreen() {
        val splashScreen = installSplashScreen()
        Thread.sleep(1000)
        splashScreen.setKeepOnScreenCondition {
            false
        }
    }

    private fun configBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.container.findViewById(R.id.nav_view)
    }

    //This method is used to request the location permission.
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }

    private fun configBottomNav() {
        configBottomNavMenuActions()
        configVisibility()
    }

    //This method is used to handle the bottom navigation menu actions.
    private fun configBottomNavMenuActions() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home_menu -> {
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_info_menu -> {
                    navController.navigate(R.id.navigation_info)
                }
            }
            true
        }
    }

    //This method is used to handle the visibility of the bottom navigation menu and the toolbar.
    private fun configVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.visibility = BottomNavigationView.VISIBLE
                    binding.toolbar.visibility = View.VISIBLE
                    binding.toolbar.title = Constants.LIST_HOUSES_FRAGMENT_TITLE
                }
                R.id.detailedHouseFragment -> {
                    navView.visibility = BottomNavigationView.GONE
                    binding.toolbar.visibility = View.GONE
                }
                R.id.navigation_info -> {
                    navView.visibility = BottomNavigationView.VISIBLE
                    binding.toolbar.visibility = View.VISIBLE
                }
                else -> {
                    navView.visibility = BottomNavigationView.GONE
                }
            }
        }
    }

    //This method is used to handle the back button press.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}