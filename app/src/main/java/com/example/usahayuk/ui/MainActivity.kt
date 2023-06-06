package com.example.usahayuk.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.R
import com.example.usahayuk.Utils
import com.example.usahayuk.Utils.token
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.databinding.ActivityMainBinding
import com.example.usahayuk.ui.community.KomunitasFragment
import com.example.usahayuk.ui.home.HomeFragment
import com.example.usahayuk.ui.konsultasi.KonsultasiFragment
import com.example.usahayuk.ui.profle.ProfileFragment

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showHomeFragment = intent.getBooleanExtra("showHomeFragment", false)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> replaceFragment(HomeFragment())
                R.id.komunitas -> replaceFragment(KomunitasFragment())
                R.id.konsultasi -> replaceFragment(KonsultasiFragment())
                R.id.profil -> replaceFragment(ProfileFragment())
                else -> {
                }
            }

            true
        }

        if (showHomeFragment) {
            replaceFragment(HomeFragment())
        }
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        var name: String
        mainViewModel.getUser().observe(this){ user ->
            Utils.token = "Bearer ${user.token}"
            name = user.name
            Utils.EXTRA_UID = user.uid

            binding.apply {
                getToken(token)
            }
        }
    }

    private fun getToken(token: String) {
        binding.apply {
            if (token.isEmpty())
                return
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    

}