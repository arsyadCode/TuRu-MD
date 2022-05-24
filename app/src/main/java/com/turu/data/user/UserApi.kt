package com.turu.data.user

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

interface UserApi {

    @POST("users/login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("users/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    )
}