package com.example.usahayuk.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.R
import com.example.usahayuk.ViewModelFactory
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.data.local.entity.User
import com.example.usahayuk.databinding.ActivityRegisterBinding
import com.example.usahayuk.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    private var checkMyPass: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        binding.txtHaveAccount.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)

        }
        playAnimation()
        setupView()
        setupAction()
        setupViewModel()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameEdt = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(500)
        val emailEdt = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordEdt = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val passConfirmation = ObjectAnimator.ofFloat(binding.confirmPasswordEdt, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.registerBtn, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.txtHaveAccount, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(nameEdt,emailEdt,passwordEdt,passConfirmation)
        }

        AnimatorSet().apply {
            playSequentially(title, together, signup, login)
            start()
        }
    }

    private fun setupView() {
        val loginTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isNameEmpty = binding.nameEditText.text.toString().trim().isEmpty()
                val isEmailEmpty = binding.emailEditText.text.toString().trim().isEmpty()
                val isPasswordEmpty = binding.passwordEditText.text.toString().trim().isEmpty()
                val isPasswordValid = binding.passwordEditText.text.toString().length >= 8
                binding.registerBtn.isEnabled =
                    !isNameEmpty && !isEmailEmpty && !isPasswordEmpty && isPasswordValid
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.nameEditText.addTextChangedListener(loginTextWatcher)
        binding.emailEditText.addTextChangedListener(loginTextWatcher)
        binding.passwordEditText.addTextChangedListener(loginTextWatcher)
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPreferences.getInstance(dataStore))
        )[RegisterViewModel::class.java]
        registerViewModel.message.observe(this){
            checkEmail(it, registerViewModel.isError)
        }
        registerViewModel.isLoading.observe(this) {
            isLoading(it)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun checkPass() {
        if (binding.passwordEditText.text.toString().trim() != binding.confirmPasswordEdt.text.toString()
                .trim()
        ) {
            binding.confirmPasswordEdt.error = resources.getString(R.string.pass_salah)
            checkMyPass = false
        } else {
            binding.confirmPasswordEdt.error = null
            checkMyPass = true
        }
    }

    private fun emailExist() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Email Salah")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun checkEmail(message: String, isError: Boolean) {
        if (!isError) {
            createdDialog()
        } else {
            when (message) {
                "Bad Request" -> {
                    emailExist()
                    binding.emailEditText.apply {
                        setText("")
                        requestFocus()
                    }
                }
                "timeout" -> {
                    Toast.makeText(this, "Sesi Habis", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(
                        this,
                        "Error $message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun createdDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Akun Berhasil Dibuat")

        alertDialogBuilder.setPositiveButton("OK") { _, _ ->
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun registerButton(){
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        binding.passwordEditText.setOnFocusChangeListener { v, focused ->
            if (v != null) {
                if (!focused) {
                    checkPass()
                }
            }
        }
        binding.confirmPasswordEdt.setOnFocusChangeListener { v, focused ->
            if (v != null) {
                if (!focused) {
                    checkPass()
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            binding.apply {
                nameEditText.clearFocus()
                emailEditText.clearFocus()
                passwordEditText.clearFocus()
                confirmPasswordEdt.clearFocus()
            }
            if (isDataValid()) {
                registerViewModel.saveUser(User(name, email, password, false))
                uploadData()
            } else {
                errorDialog()
            }
        }
    }

    private fun uploadData() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        registerViewModel.postSignupData(name, email, password)
    }

    private fun isDataValid(): Boolean {
        return binding.nameEditText.isNameValid &&
                binding.emailEditText.isEmailValid &&
                binding.passwordEditText.isPassValid &&
                checkMyPass

    }

    private fun errorDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Masukkan data dengan benar")

        alertDialogBuilder.setPositiveButton("OK"){ dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun setupAction() {
        binding.txtHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerBtn.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditText.error = "Masukkan nama"
                }
                email.isEmpty() -> {
                    binding.emailEditText.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Masukkan password"
                }
                else -> {
                   registerButton()
                }
            }
        }
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    binding.passwordEditText.error = "Kata sandi harus terdiri dari minimal 8 karakter"
                } else {
                    binding.passwordEditText.error = null
                }
            }
        })
    }

}