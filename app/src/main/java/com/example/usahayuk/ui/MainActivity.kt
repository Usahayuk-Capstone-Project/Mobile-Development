package com.example.usahayuk.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.usahayuk.R
import com.example.usahayuk.Utils
import com.example.usahayuk.Utils.token
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.WelcomeActivity
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.beranda -> {
                    // Check if the current destination is already the selected fragment
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                }
                R.id.komunitas -> {
                    if (navController.currentDestination?.id != R.id.komunitasFragment) {
                        navController.navigate(R.id.komunitasFragment)
                    }
                }
                R.id.konsultasi -> {
                    if (navController.currentDestination?.id != R.id.konsultasiFragment) {
                        navController.navigate(R.id.konsultasiFragment)
                    }
                }
                R.id.profil -> {
                    if (navController.currentDestination?.id != R.id.profileFragment) {
                        navController.navigate(R.id.profileFragment)
                    }
                }
            }
            true
        }

        setupNavController()

        val showHomeFragment = intent.getBooleanExtra("showHomeFragment", false)
        if (showHomeFragment) {
            navController.navigate(R.id.homeFragment)
        }

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                navController.navigate(R.id.homeFragment)
            } else {
                intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        mainViewModel.getUser().observe(this) { user ->
            token = "Bearer ${user.token}"
            Utils.EXTRA_UID = user.uid
        }
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.beranda -> navController.navigate(R.id.homeFragment)
                R.id.komunitas -> navController.navigate(R.id.komunitasFragment)
                R.id.konsultasi -> navController.navigate(R.id.konsultasiFragment)
                R.id.profil -> navController.navigate(R.id.profileFragment)
            }
            true
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id != R.id.homeFragment) {
            // If the current destination is not the HomeFragment, navigate back to it
            navController.navigate(R.id.homeFragment)
            binding.bottomNavigationView.selectedItemId = R.id.beranda // Set the active icon to Home
        } else {
            finishAffinity()// Otherwise, perform the default back button behavior
        }
    }
}