package com.turu.data.user

import com.google.gson.annotations.SerializedName


data class Data(

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("id")
    val id: String? = null
)