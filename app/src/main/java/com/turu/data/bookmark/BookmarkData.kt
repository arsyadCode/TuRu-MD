package com.turu.data.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkData(

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