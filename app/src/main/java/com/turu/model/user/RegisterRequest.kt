package com.turu.model.user

import com.google.gson.annotations.SerializedName

class RegisterRequest {

    @field:SerializedName("name")
    var name: String? = null

    @field:SerializedName("email")
    var email: String? = null

    @field:SerializedName("password")
    var password: String? = null
}