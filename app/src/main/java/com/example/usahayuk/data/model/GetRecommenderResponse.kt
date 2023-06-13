package com.example.usahayuk.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetRecommenderResponse(

	@field:SerializedName("Hasil_rekomendasi")
	val hasilRekomendasi: List<String>,

	@field:SerializedName("code")
	val code: Int
) : Parcelable
