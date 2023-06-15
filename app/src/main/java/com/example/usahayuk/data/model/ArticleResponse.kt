package com.example.usahayuk.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ArticleResponse(

	@field:SerializedName("message")
	val message: String
) : Parcelable


data class ArticleMainResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("user")
	val user: ArticleDataResponse
)

@Parcelize
data class ArticleDataResponse(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("penulis")
	val penulis: String,

	@field:SerializedName("updateAt")
	val updateAt: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("tags")
	val tags: String
) :Parcelable

