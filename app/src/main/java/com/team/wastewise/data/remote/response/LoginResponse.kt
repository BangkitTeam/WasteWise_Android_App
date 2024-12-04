package com.team.wastewise.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("token")
    val token: String // The authentication token for the user.
)