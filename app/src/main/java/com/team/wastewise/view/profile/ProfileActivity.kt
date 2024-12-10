package com.team.wastewise.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.wastewise.R
import com.team.wastewise.data.remote.retrofit.ApiConfig
import com.team.wastewise.databinding.ActivityProfileBinding
import com.team.wastewise.pref.SessionManager
import com.team.wastewise.view.login.LoginActivity
import com.team.wastewise.view.setting.SettingActivity
import com.team.wastewise.view.setting.SettingViewModel
import com.team.wastewise.view.setting.SettingViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private val settingViewModel: SettingViewModel by viewModels {
        val sessionManager = SessionManager(applicationContext)
        val apiService = ApiConfig.getApiService(sessionManager)
        SettingViewModelFactory(apiService)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.profile)

        setupObservers()

        // Fetch settings
        settingViewModel.fetchUserSettings()

        // Handle toolbar navigation
        binding.toolbar.setNavigationOnClickListener {
            finish() // Close the activity and navigate back
        }

        // Navigate to Settings
        binding.clickableSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        // Navigate to About
        binding.clickableAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        // Navigate to Privacy Policy
        binding.clickablePrivacy.setOnClickListener {
            startActivity(Intent(this, PolicyActivity::class.java))
        }

        // Navigate to Terms
        binding.clickableTerms.setOnClickListener {
            startActivity(Intent(this, TermUseActivity::class.java))
        }

        // Logout button action
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupObservers() {
        settingViewModel.userSettings.observe(this) { settings ->
            binding.tvUsername.setText(settings.username)
        }

        settingViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

}