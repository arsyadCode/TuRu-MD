package com.turu.data.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("images")
	val images:  List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
