package com.team.wastewise.di

import android.content.Context
import com.team.wastewise.data.preference.UserPreference
import com.team.wastewise.data.preference.dataStore
import com.team.wastewise.data.remote.UploadRepository
import com.team.wastewise.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUploadRepository(context: Context): UploadRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { userPreference.getSession().first() }
        val apiService = ApiConfig.getApiService(userPreference)
        return UploadRepository.getInstance(apiService)
    }
}