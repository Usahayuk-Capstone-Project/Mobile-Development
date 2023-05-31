package com.example.usahayuk.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.usahayuk.databinding.ActivityRegisterBinding
import com.example.usahayuk.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        binding.txtHaveAccount.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            supportActionBar!!.hide()
        }
        playAnimation()


    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailEdt = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val emailLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val phoneEdt = ObjectAnimator.ofFloat(binding.phoneEditText, View.ALPHA, 1f).setDuration(500)
        val phoneLayout = ObjectAnimator.ofFloat(binding.phoneEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val nameEdt = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(500)
        val nameLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordEdt = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(nameEdt,nameLayout,phoneEdt,phoneLayout,emailEdt,emailLayout,passwordEdt,passwordLayout)
        }

        AnimatorSet().apply {
            playSequentially(title,message,together,login)
            start()
        }
    }
}