package com.turu.data.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: HistoryData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HistoryData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
