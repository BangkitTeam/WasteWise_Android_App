package com.team.wastewise.view.settingedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.SettingEditRepository
import com.team.wastewise.data.remote.response.UpdateUserResponse
import com.team.wastewise.data.remote.response.UserSettingsResponse
import com.team.wastewise.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import com.team.wastewise.data.Result

class SettingEditViewModel(private val repository: SettingEditRepository) : ViewModel() {

    private val _updateUser = MutableLiveData<Result<UpdateUserResponse>>()
    var updateUser: MutableLiveData<Result<UpdateUserResponse>> = _updateUser

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun updateUserSettings(
        token: String,
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = repository.settingEdit(token, username, email, password)
            _updateUser.value = result
        }
    }
}