package com.team.wastewise.data.preference

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
