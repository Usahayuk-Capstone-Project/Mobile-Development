package com.example.usahayuk.ui.profle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usahayuk.data.Repository
import com.example.usahayuk.data.model.UserResponse

class ProfileViewModel (application: Application) : AndroidViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile fragment"
    }
    val text: LiveData<String> = _text

    private val mRepository: Repository = Repository(application)

    fun setUser(): LiveData<UserResponse>{ return mRepository.setUser()}

    fun getUser(token: String, uid : String) = mRepository.getUser(token, uid)

    fun setUpdateUser(token: String, uid: String,name: String, email: String) = mRepository.setUpdateUser(token,uid, name, email)
}