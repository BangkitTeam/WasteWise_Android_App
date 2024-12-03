package com.team.wastewise.data.remote.retrofit

import com.team.wastewise.data.remote.response.FileUploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Header
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
}