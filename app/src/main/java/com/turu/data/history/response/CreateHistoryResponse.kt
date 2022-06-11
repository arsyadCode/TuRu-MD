package com.turu.data.history.response

import com.google.gson.annotations.SerializedName

data class CreateHistoryResponse(

	@field:SerializedName("data")
	val data: HistoryData? = null,

	@field:SerializedName("message")
	val message: String? = null
)


