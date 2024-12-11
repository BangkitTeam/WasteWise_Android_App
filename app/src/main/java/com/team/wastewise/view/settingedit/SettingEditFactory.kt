package com.team.wastewise.view.settingedit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.wastewise.data.remote.SettingEditRepository
import com.team.wastewise.di.Injection

class SettingEditFactory(private val repository: SettingEditRepository) : ViewModelProvider.NewInstanceFactory() {

    //create an instance
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingEditViewModel::class.java)) {
            return SettingEditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class" + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SettingEditFactory? = null

        fun getInstance(context: Context): SettingEditFactory =
            instance ?: synchronized(this) {
                instance ?: SettingEditFactory(
                    Injection.settingEditRepository(context)
                )
            }.also { instance = it }
    }
}