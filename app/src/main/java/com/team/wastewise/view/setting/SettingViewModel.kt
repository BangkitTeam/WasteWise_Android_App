package com.team.wastewise.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.response.UserSettingsResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch

class SettingViewModel(private val apiService: ApiService) : ViewModel() {

    private val _userSettings = MutableLiveData<UserSettingsResponse>()
    val userSettings: LiveData<UserSettingsResponse> = _userSettings

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchUserSettings() {
        viewModelScope.launch {
            try {
                val response = apiService.getUserSettings()
                _userSettings.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}