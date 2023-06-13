package com.example.usahayuk.ui.home

import android.content.ContentValues
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.usahayuk.data.model.GetRecommenderResponse
import com.example.usahayuk.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel : ViewModel() {
    val recommendationLiveData: MutableLiveData<List<String>> = MutableLiveData()

    fun getRecommendations(userId: String) {
        val client = ApiConfig.getApiService().getRecommender(userId)
        client.enqueue(object : Callback<GetRecommenderResponse> {
            override fun onResponse(
                call: Call<GetRecommenderResponse>,
                response: Response<GetRecommenderResponse>
            ) {
                if (response.isSuccessful) {
                    val recommendationResponse = response.body()
                    recommendationResponse?.let {
                        recommendationLiveData.value = it.hasilRekomendasi
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    Log.e(ContentValues.TAG, "Response Body: ${response.body().toString()}")

                }
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<GetRecommenderResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }
}