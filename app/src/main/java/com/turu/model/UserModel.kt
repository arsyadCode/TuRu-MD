package com.turu.model

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val photo: String,
    val token: String,
    val isLogin: Boolean
)