package com.example.usahayuk.data.model

import com.google.gson.annotations.SerializedName

data class RecomenderResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("user")
	val user: UserData
)

data class UserData(

	@field:SerializedName("skala_usaha")
	val skalaUsaha: String,

	@field:SerializedName("modal_usaha")
	val modalUsaha: String,

	@field:SerializedName("jenis_usaha")
	val jenisUsaha: String,

	@field:SerializedName("jenis_lokasi")
	val jenisLokasi: String,

	@field:SerializedName("gender_targetpelanggan")
	val genderTargetpelanggan: String,

	@field:SerializedName("bidang_usaha")
	val bidangUsaha: String,

	@field:SerializedName("status_targetpelanggan")
	val statusTargetpelanggan: String,

	@field:SerializedName("omset_usaha")
	val omsetUsaha: String,

	@field:SerializedName("usia_targetpelanggan")
	val usiaTargetpelanggan: String,

	@field:SerializedName("perkerjaan_targetpelanggan")
	val perkerjaanTargetpelanggan: String
)
