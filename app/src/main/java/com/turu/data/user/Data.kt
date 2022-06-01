package com.turu.data.user

import com.google.gson.annotations.SerializedName


data class Data(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("token")
    val token: String? = null,
)