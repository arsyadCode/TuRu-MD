package com.turu.data.history.response

import com.google.gson.annotations.SerializedName

data class GetHistoryUserIdResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
