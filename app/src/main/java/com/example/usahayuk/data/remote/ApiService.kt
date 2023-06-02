package com.example.usahayuk.data.remote

import com.example.usahayuk.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/signup")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("api/auth/signin")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/user/{id}")
    fun getUser(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Call<UserResponse>

    fun updateUser(@Body request: UpdateRequest): Call<UserResponse>









}