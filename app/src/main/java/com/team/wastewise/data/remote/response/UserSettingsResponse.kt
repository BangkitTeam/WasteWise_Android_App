package com.team.wastewise.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserSettingsResponse (
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)