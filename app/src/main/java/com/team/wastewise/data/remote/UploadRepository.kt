package com.team.wastewise.data.remote

import com.google.gson.Gson
import com.team.wastewise.data.remote.response.FileUploadResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class UploadRepository private constructor(
    private val apiService: ApiService
){
    suspend fun uploadImage(imageFile: java.io.File) : Result<FileUploadResponse> {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        return try {
            val client = apiService.uploadImage(multipartBody)
            Result.success(client)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            Result.failure(Exception(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: UploadRepository? = null
        fun getInstance(apiService: ApiService) : UploadRepository =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }
    }
}