package com.team.wastewise.data.remote.retrofit

import com.team.wastewise.data.remote.response.FileUploadResponse
import com.team.wastewise.data.remote.response.LoginResponse
import com.team.wastewise.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("upload/upload")
    suspend fun uploadImage(
//        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ) : FileUploadResponse
/*
    // Endpoint to register a new user
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse*/

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // Data classes for the requests
    data class RegisterRequest(
        val username: String,
        val email: String,
        val password: String
    )

    data class LoginRequest(
        val email: String,
        val password: String
    )
}