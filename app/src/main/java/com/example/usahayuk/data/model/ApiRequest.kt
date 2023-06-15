package com.example.usahayuk.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    @SerializedName("bidang_usaha") val bidangUsaha: String,
    @SerializedName("gender_targetpelanggan") val genderTargetpelanggan: List<String>,
    @SerializedName("jenis_lokasi_") val jenisLokasi: List<String>,
    @SerializedName("modal_usaha") val modalUsaha: String,
    @SerializedName("omset_usaha") val omsetUsaha: String,
    @SerializedName("skala_usaha") val skalaUsaha: String,
    @SerializedName("pekerjaan_targetpelanggan") val pekerjaanTargetPelanggan: List<String>,
    @SerializedName("status_targetpelanggan") val statusTargetpelanggan: List<String>,
    @SerializedName("usia_targetpelanggan") val usiaTargetpelanggan: List<String>,
)
data class RecommendationRequest(
    @SerializedName("user_id") val user_id :String
)
@Parcelize
data class PostArticleRequest(
    @field:SerializedName("penulis")
    val penulis: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("tags")
    val tags: String
) : Parcelable



