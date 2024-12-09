package com.team.wastewise.di

import android.content.Context
import com.team.wastewise.data.preference.UserPreference
import com.team.wastewise.data.preference.dataStore
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.Repository
import com.team.wastewise.data.remote.SettingEditRepository
import com.team.wastewise.data.remote.UploadRepository
import com.team.wastewise.data.remote.retrofit.ApiConfig
import com.team.wastewise.pref.SessionManager

object Injection {
    fun provideUploadRepository(context: Context): UploadRepository {
        val sessionManager = SessionManager(context)
        val apiService = ApiConfig.getApiService(sessionManager)
        return UploadRepository.getInstance(apiService)
    }

    // Provides an instance of the Repository, ensuring ApiService is initialized.
    fun repository(context: Context): Repository {
        val sessionManager = SessionManager(context)

        // Initialize ApiService using ApiConfig.
        val apiService = ApiConfig.getApiService(sessionManager)

        // Return a singleton instance of Repository with ApiService.
        return Repository.getInstance(apiService)
    }

    // Provides an instance of the Setting Edit Repository, ensuring ApiService is initialized.
    fun settingEditRepository(context: Context): SettingEditRepository {
        val sessionManager = SessionManager(context)

        // Initialize ApiService using ApiConfig.
        val apiService = ApiConfig.getApiService(sessionManager)

        // Return a singleton instance of Repository with ApiService.
        return SettingEditRepository.getInstance(apiService)
    }

    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val sessionManager = SessionManager(context)
        val apiService = ApiConfig.getApiService(sessionManager)
        return FavoriteRepository.getInstance(apiService)
    }
}