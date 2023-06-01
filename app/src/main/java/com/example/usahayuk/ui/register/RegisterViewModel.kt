package com.example.usahayuk.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usahayuk.data.local.datastore.LoginPreferences
import com.example.usahayuk.data.local.entity.User
import com.example.usahayuk.data.model.RegisterRequest
import com.example.usahayuk.data.model.RegisterResponse
import com.example.usahayuk.data.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel (private val pref: LoginPreferences) : ViewModel(){
    private val _signUp = MutableLiveData<RegisterResponse>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var isError: Boolean = false

    fun registerData(name: String, email: String, password: String) {
        _isLoading.value = true
        val registerRequest = RegisterRequest(name,email, password)
        val client = ApiConfig.getApiService().register(registerRequest)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    isError = false
                    Log.e("signupResponse", "onResponse: ${response.message()}")
                    _message.value = responseBody?.message.toString()
                    _signUp.value = response.body()
                } else {
                    isError = true
                    _message.value = response.message()
                    Log.e("Signup", "onResponse: ${response.message()} ")

                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("signupFailure", "onFailure: ${t.message}")
                _isLoading.value = false
            }
        })
    }

    fun postSignupData(name: String, email: String, password: String){
        viewModelScope.launch {
            registerData(name, email, password)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}