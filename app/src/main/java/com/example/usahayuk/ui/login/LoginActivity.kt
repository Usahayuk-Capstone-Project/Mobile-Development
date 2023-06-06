package com.example.usahayuk.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.R
import com.example.usahayuk.Utils
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.data.model.LoginResponse
import com.example.usahayuk.data.local.entity.User
import com.example.usahayuk.data.model.LoginRequest
import com.example.usahayuk.data.remote.ApiConfig
import com.example.usahayuk.ui.register.RegisterActivity
import com.example.usahayuk.databinding.ActivityLoginBinding
import com.example.usahayuk.ui.MainActivity
import com.google.android.gms.common.SignInButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        val signInButton: SignInButton = findViewById(R.id.signin_google)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        playAnimation()
        setupAction()
        setupInput()
        setupViewModel()
    }
    private fun setupAction(){
        binding.loginButton.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.inputEmail.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.inputPassword.error = "Masukkan password"
                }
                else -> {
                    login()
                }
            }
        }

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

    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]

    }

    private fun login(){
        isLoading(true)
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

        val loginRequest = LoginRequest(email, password)

        val client = ApiConfig.getApiService().login(loginRequest)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    Utils.token = "Bearer ${responseBody.userCredential.stsTokenManager.accessToken}"
                    isLoading(false)
                    Utils.EXTRA_UID = responseBody.userCredential.uid
                    loginViewModel.saveUser(
                        User(
                            responseBody.userCredential.uid,
                            responseBody.userCredential.displayName,
                            responseBody.userCredential.stsTokenManager.accessToken,
                            true
                        )
                    )
                    savePref()
                    Toast.makeText(this@LoginActivity, "Login Sudah Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("showHomeFragment", true)
                    startActivity(intent)
                }else {
                    isLoading(false)
                    Toast.makeText(this@LoginActivity, "Email atau Password anda salah",Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }

        })

    }

    private fun savePref(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.apply {
            putString("STRING_KEY", "login")
        }.apply()
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

    private fun isLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
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
        val signup = ObjectAnimator.ofFloat(binding.signinGoogle, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(emailEdt, passwordEdt)
        }

        AnimatorSet().apply {
            playSequentially(title, message, together, login, signup)
            start()
        }
    }
}