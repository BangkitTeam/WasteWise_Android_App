package com.team.wastewise.data.remote

import com.team.wastewise.data.remote.response.RegisterResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.team.wastewise.data.Result

class Repository private constructor(
    private val apiService: ApiService
){

    // Function to register a new user
    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) { // Use Dispatchers.IO for network calls
            try {
                // Make the network request using Retrofit
                val response = apiService.register(username, email, password)

                // Check if the response is successful
                // In this case, Retrofit's default behavior handles it
                // Success case
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
        private var INSTANCE: Repository? = null

        // Provide a single instance of the Repository
        fun getInstance(apiService: ApiService): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(apiService)
            }.also { INSTANCE = it }
    }
}