package com.example.usahayuk.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.data.local.entity.User
import com.example.usahayuk.data.model.UserResponse

class MainViewModel(private val pref: LoginPreferences) : ViewModel() {
    private val user = MutableLiveData<UserResponse>()
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

}