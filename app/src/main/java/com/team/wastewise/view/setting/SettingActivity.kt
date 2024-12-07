package com.team.wastewise.view.setting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.wastewise.data.preference.UserPreference
import com.team.wastewise.data.remote.retrofit.ApiConfig
import com.team.wastewise.databinding.ActivitySettingBinding
import com.team.wastewise.data.preference.dataStore // Import the dataStore extension property

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    private val settingViewModel: SettingViewModel by viewModels {
        val userPreference = UserPreference.getInstance(applicationContext.dataStore)
        val apiService = ApiConfig.getApiService(userPreference)
        SettingViewModelFactory(apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        // Fetch settings
        settingViewModel.fetchUserSettings()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish() // Close activity on toolbar navigation click
        }
    }

    private fun setupObservers() {
        settingViewModel.userSettings.observe(this) { settings ->
            binding.usernameEditText.setText(settings.username)
            binding.emailEditText.setText(settings.email)
            binding.passwordEditText.setText(settings.password)
        }

        settingViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}
