package com.turu.data.bookmark

import com.google.gson.annotations.SerializedName

data class CreateBookmarkResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
