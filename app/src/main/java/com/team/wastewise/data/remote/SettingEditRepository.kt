package com.team.wastewise.data.remote

import com.team.wastewise.data.Result
import com.team.wastewise.data.remote.response.UpdateUserResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SettingEditRepository private constructor(
    private val apiService: ApiService
) {
    // Function to edit user setting
    suspend fun settingEdit(token: String, username: String, email: String, password: String): Result<UpdateUserResponse> {
        return withContext(Dispatchers.IO) { // Use Dispatchers.IO for network calls
            try {
                // Create the request body
                val request = ApiService.EditUserRequest(
                    username = username,
                    email = email,
                    password = password
                )

                // Make the network request using Retrofit
                val response = apiService.editUserSettings(token, request)

                // Return success with the response
                Result.Success(response)
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 400, 500 status codes)
                Result.Error("HTTP error: ${e.message()}")
            } catch (e: Exception) {
                // Handle unexpected errors (e.g., network issues)
                Result.Error("Unexpected error: ${e.message}")
            }
        }
    }

    // Singleton pattern to provide a single instance of the Repository
    companion object {
        @Volatile
        private var INSTANCE: SettingEditRepository? = null

        fun getInstance(apiService: ApiService): SettingEditRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingEditRepository(apiService)
            }.also { INSTANCE = it }
    }
}