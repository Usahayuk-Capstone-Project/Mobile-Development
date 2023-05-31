package com.example.usahayuk.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.data.local.entity.User

class MainViewModel(private val pref: LoginPreferences) : ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}