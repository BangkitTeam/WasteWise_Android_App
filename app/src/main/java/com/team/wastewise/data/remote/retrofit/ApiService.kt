package com.team.wastewise.data.remote.retrofit

import com.team.wastewise.data.remote.response.FileUploadResponse
import com.team.wastewise.data.remote.response.LoginResponse
import com.team.wastewise.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
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

    // Endpoint to register a new user
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    // Endpoint to log in an existing user
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}