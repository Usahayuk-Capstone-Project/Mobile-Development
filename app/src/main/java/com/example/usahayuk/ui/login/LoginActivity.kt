package com.example.usahayuk.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.ui.register.RegisterActivity
import com.example.usahayuk.databinding.ActivityLoginBinding
import com.example.usahayuk.ui.MainActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        playAnimation()

        binding.txtRegister.setOnClickListener {
            val intent = Intent (this, RegisterActivity::class.java)
            startActivity(intent)
            supportActionBar!!.hide()
        }

        binding.seePassword.setOnClickListener {
            if (binding.seePassword.isChecked) {
                binding.inputPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.inputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.loginButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("showHomeFragment", true)
            startActivity(intent)
            finish()

        }
    }


    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setupInput(){

        val loginTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val isEmailEmpty = binding.inputEmail.text.toString().trim().isEmpty()
                val isPasswordEmpty = binding.inputPassword.text.toString().trim().isEmpty()
                val isPasswordValid = binding.inputPassword.text.toString().length >= 8
                binding.loginButton.isEnabled = !isEmailEmpty && !isPasswordEmpty && isPasswordValid
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        binding.inputEmail.addTextChangedListener(loginTextWatcher)
        binding.inputPassword.addTextChangedListener(loginTextWatcher)
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailEdt =
            ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(500)
        val passwordEdt =
            ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(emailEdt, passwordEdt)
        }

        AnimatorSet().apply {
            playSequentially(title, message, together, login, signup)
            start()
        }
    }
}