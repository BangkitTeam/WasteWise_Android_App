package com.team.wastewise.di

import android.content.Context
import com.team.wastewise.data.preference.UserPreference
import com.team.wastewise.data.preference.dataStore
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.Repository
import com.team.wastewise.data.remote.UploadRepository
import com.team.wastewise.data.remote.retrofit.ApiConfig

object Injection {
    fun provideUploadRepository(context: Context): UploadRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { userPreference.getSession().first() }
        val apiService = ApiConfig.getApiService(userPreference)
        return UploadRepository.getInstance(apiService)
    }

    // Provides an instance of the Repository, ensuring ApiService is initialized.
    fun repository(context: Context): Repository {
        val userPreference = UserPreference.getInstance(context.dataStore)

        // Initialize ApiService using ApiConfig.
        val apiService = ApiConfig.getApiService(userPreference)

        // Return a singleton instance of Repository with ApiService.
        return Repository.getInstance(apiService)
    }

    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(userPreference)
        return FavoriteRepository.getInstance(apiService)
    }
}