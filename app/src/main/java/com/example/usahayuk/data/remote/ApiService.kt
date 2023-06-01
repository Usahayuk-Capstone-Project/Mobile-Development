package com.example.usahayuk.data.remote

import com.example.usahayuk.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/signup")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("api/auth/signin")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    fun getUser(
        @Header("Authorization") authToken: String
    ): Call<UserResponse>









}