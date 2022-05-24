package com.turu.data.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

	@field:SerializedName("BookmarkResponse")
	val bookmarkResponse: List<BookmarkResponseItem?>? = null
)

data class BookmarkResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
