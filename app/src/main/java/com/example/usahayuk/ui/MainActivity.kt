package com.example.usahayuk.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.usahayuk.R
import com.example.usahayuk.databinding.ActivityMainBinding
import com.example.usahayuk.ui.community.KomunitasFragment
import com.example.usahayuk.ui.home.HomeFragment
import com.example.usahayuk.ui.mybussiness.UsahakuFragment
import com.example.usahayuk.ui.profle.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showHomeFragment = intent.getBooleanExtra("showHomeFragment", false)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> replaceFragment(HomeFragment())
                R.id.komunitas -> replaceFragment(KomunitasFragment())
                R.id.usahaku -> replaceFragment(UsahakuFragment())
                R.id.profil -> replaceFragment(ProfileFragment())
                else -> {
                }
            }

            true
        }

        if (showHomeFragment) {
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}