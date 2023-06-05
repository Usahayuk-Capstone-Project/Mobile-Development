package com.example.usahayuk.data

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usahayuk.data.model.UpdateRequest
import com.example.usahayuk.data.model.UserResponse
import com.example.usahayuk.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(application: Application) {
    private val user = MutableLiveData<UserResponse>()

    fun setUser(): LiveData<UserResponse>{
        return user
    }

    fun getUser(token: String, uid: String) {
        val client = ApiConfig.getApiService().getUser("Bearer $token",uid)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }

    fun setUpdateUser(token: String, uid:String, name: String, email: String) {
        val updateRequest = UpdateRequest(token, name, email)
        val client = ApiConfig.getApiService().updateProfile(uid,updateRequest)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }
}