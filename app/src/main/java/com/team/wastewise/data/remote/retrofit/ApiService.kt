package com.team.wastewise.data.remote.retrofit

import com.team.wastewise.data.remote.response.AddFavoriteRequest
import com.team.wastewise.data.remote.response.FavoriteData
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.data.remote.response.FavoriteResponse
import com.team.wastewise.data.remote.response.FileUploadResponse
import com.team.wastewise.data.remote.response.LoginResponse
import com.team.wastewise.data.remote.response.RegisterResponse
import com.team.wastewise.data.remote.response.UpdateUserResponse
import com.team.wastewise.data.remote.response.UserSettingsResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("user/settings")
    suspend fun getUserSettings(): UserSettingsResponse

    // Endpoint to edit settings by Token
    @POST("user/settings")
    suspend fun editUserSettings(
        @Body request: EditUserRequest
    ): UpdateUserResponse

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

    data class EditUserRequest(
        val username: String,
        val email: String,
        val password: String
    )

    @GET("/user/favorite")
    suspend fun getAllFavorite(): FavoriteResponse

    @POST("/user/favorite")
    suspend fun addFavorite(): AddFavoriteRequest

    @DELETE("/user/favorite/{id}")
    suspend fun deleteFavorite(
        @Path("id") id: Int
    ): FavoriteItem
}