package com.turu.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterRequest {

    @field:SerializedName("name")
    @Expose
    var name: String? = null

    @field:SerializedName("email")
    @Expose
    var email: String? = null

    @field:SerializedName("password")
    @Expose
    var password: String? = null
}