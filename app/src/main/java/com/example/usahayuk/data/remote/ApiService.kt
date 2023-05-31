package com.example.usahayuk.data.remote

import com.example.usahayuk.data.model.LoginResponse
import com.example.usahayuk.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/auth/signup")
    fun register(
        @Field("displayName") name :String,
        @Field("email") email :String,
        @Field("password") password :String,
        @Field("phoneNumber") phoneNumber : Any
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/auth/signin")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>



}