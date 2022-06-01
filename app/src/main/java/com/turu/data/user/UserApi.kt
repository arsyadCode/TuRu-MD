package com.turu.data.user

import com.turu.model.user.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface UserApi {

    @POST("users/login")
    fun userLogin(
        @Body req: LoginRequest
    ): Call<LoginResponse>

    @POST("users/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    )
}