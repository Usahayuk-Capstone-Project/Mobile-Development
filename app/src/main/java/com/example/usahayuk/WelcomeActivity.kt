package com.example.usahayuk

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.databinding.ActivityWelcomeBinding
import com.example.usahayuk.ui.MainActivity
import com.example.usahayuk.ui.MainViewModel
import com.example.usahayuk.ui.login.LoginActivity
import com.example.usahayuk.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        playAnimation()
        setupAction()
    }
    private fun navigateToHomeFragment() {
        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
        intent.putExtra("showHomeFragment", true)
        startActivity(intent)
        finish()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]
        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                navigateToHomeFragment()
            }
        }

    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val next = ObjectAnimator.ofFloat(binding.nextButton, View.ALPHA, 1f).setDuration(500)
        val logo = ObjectAnimator.ofFloat(binding.imgLogo, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(logo,title, desc, next)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}