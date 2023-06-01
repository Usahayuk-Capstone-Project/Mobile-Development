package com.example.usahayuk.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("alamat_email") val email: String,
    @SerializedName("kata_sandi") val password: String
)

data class RegisterRequest(
    @SerializedName("nama_lengkap") val name: String,
    @SerializedName("alamat_email") val email: String,
    @SerializedName("kata_sandi") val password: String
)
