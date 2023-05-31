package com.example.usahayuk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.ui.MainViewModel
import com.example.usahayuk.ui.login.LoginViewModel

class ViewModelFactory (private val pref:LoginPreferences) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> { MainViewModel(pref) as T }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> { LoginViewModel(pref) as T }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}