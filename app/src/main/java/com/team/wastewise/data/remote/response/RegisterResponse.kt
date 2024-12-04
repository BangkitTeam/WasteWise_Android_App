package com.team.wastewise.data.remote.response

import com.google.gson.annotations.SerializedName

// Represents the response for a registration request.
data class RegisterResponse (
    @field:SerializedName("id")
    val id: Int, // The ID of the registered user.

    @field:SerializedName("username")
    val username: String, // The username of the registered user.

    @field:SerializedName("email")
    val email: String // The email address of the registered user.
)