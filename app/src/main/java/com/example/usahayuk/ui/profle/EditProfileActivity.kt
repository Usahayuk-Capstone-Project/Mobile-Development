package com.example.usahayuk.ui.profle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.Utils
import com.example.usahayuk.databinding.ActivityEditProfileBinding
import com.example.usahayuk.ui.MainActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
        binding.txtSimpan.setOnClickListener {
            binding.apply {
                val name = binding.nameEditText
                val email = binding.emailEditText

                profileViewModel.setUpdateUser(
                    Utils.token,
                    Utils.EXTRA_UID,
                    name.text.toString(),
                    email.text.toString()
                )
                AlertDialog.Builder(this@EditProfileActivity).apply {
                    setTitle("Profil Berhasil di Update!")
                    setMessage("Update profile berhasil")
                    setPositiveButton("Ok") { _, _ ->
                        setupViewModel()
                        val intent = Intent (this@EditProfileActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        profileViewModel.getUser(Utils.token, Utils.EXTRA_UID)
    }
}