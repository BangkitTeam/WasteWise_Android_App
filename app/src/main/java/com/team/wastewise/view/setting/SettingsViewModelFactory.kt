package com.team.wastewise.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.wastewise.data.remote.retrofit.ApiService

class SettingViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
