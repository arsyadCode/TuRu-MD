package com.turu.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {

    @field:SerializedName("email")
    @Expose
    var email: String? = null

    @field:SerializedName("password")
    @Expose
    var password: String? = null
}