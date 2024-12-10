package com.team.wastewise.data.remote

import com.google.gson.Gson
import com.team.wastewise.data.remote.response.AddFavoriteRequest
import com.team.wastewise.data.remote.response.FavoriteData
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.data.remote.response.FavoriteResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import retrofit2.HttpException

class FavoriteRepository private constructor(
    private val apiService: ApiService
)  {
    suspend fun getAllFavorite(): Result<List<FavoriteItem>> {
        return try {
            val client = apiService.getAllFavorite()
            Result.success(client.data.favorite)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FavoriteResponse::class.java)
            Result.failure(Exception(errorResponse.message))
        }
    }

    suspend fun addFavorite(userRecommendationId: Int): Result<Unit> {
        return try {
            apiService.addFavorite(userRecommendationId)
            Result.success(Unit)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FavoriteResponse::class.java)
            Result.failure(Exception(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(apiService: ApiService) : FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService)
            }.also { instance = it }
    }
}