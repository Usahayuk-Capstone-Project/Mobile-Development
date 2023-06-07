package com.example.usahayuk.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field
import retrofit2.http.Header

data class LoginRequest (
    @SerializedName("alamat_email") val email: String,
    @SerializedName("kata_sandi") val password: String
)

data class RegisterRequest(
    @SerializedName("nama_lengkap") val name: String,
    @SerializedName("alamat_email") val email: String,
    @SerializedName("kata_sandi") val password: String
)

data class UpdateRequest(
    @SerializedName("Authorization") val authToken: String,
    @SerializedName("nama_lengkap") val name: String,
    @SerializedName("alamat_email") val email: String
)

data class PostDataRequest(
    @SerializedName("skala_usaha") val skalaUsaha: String,
    @SerializedName("modal_usaha") val modalUsaha: String,
    @SerializedName("jenis_usaha") val jenisUsaha: String,
    @SerializedName("jenis_lokasi") val jenisLokasi: List<String>,
    @SerializedName("gender_targetpelanggan") val genderTargetpelanggan: List<String>,
    @SerializedName("bidang_usaha") val bidangUsaha: String,
    @SerializedName("status_targetpelanggan") val statusTargetpelanggan: List<String>,
    @SerializedName("omset_usaha") val omsetUsaha: String,
    @SerializedName("usia_targetpelanggan") val usiaTargetpelanggan: List<String>,
    @SerializedName("perkerjaan_targetpelanggan") val perkerjaanTargetpelanggan: List<String>
)

