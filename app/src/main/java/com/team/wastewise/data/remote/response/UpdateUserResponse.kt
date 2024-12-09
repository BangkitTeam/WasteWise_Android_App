package com.team.wastewise.data.remote.response

data class UpdateUserResponse(
    val message: String,
    val updatedUser: UpdatedUser
)

data class UpdatedUser(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val profilePicture: String?,
    val createdAt: String,
    val updatedAt: String
)