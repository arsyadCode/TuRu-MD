package com.turu.data.bookmark.response

import com.google.gson.annotations.SerializedName

data class CreateBookmarkResponse(

    @field:SerializedName("data")
	val data: BookmarkData? = null,

    @field:SerializedName("message")
	val message: String? = null
)


