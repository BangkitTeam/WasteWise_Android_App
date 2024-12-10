package com.team.wastewise.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.team.wastewise.R
import com.team.wastewise.data.remote.retrofit.ApiConfig
import com.team.wastewise.databinding.ActivitySettingBinding
import com.team.wastewise.pref.SessionManager
import com.team.wastewise.view.settingedit.SettingEditActivity

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    private val settingViewModel: SettingViewModel by viewModels {
        val sessionManager = SessionManager(applicationContext)
        val apiService = ApiConfig.getApiService(sessionManager)
        SettingViewModelFactory(apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        moveToEditData()

        // Fetch settings
        settingViewModel.fetchUserSettings()

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.setting)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish() // Close activity on toolbar navigation click
        }
    }

    private fun setupObservers() {
        settingViewModel.userSettings.observe(this) { settings ->
            binding.usernameText.setText(settings.username)
            binding.emailText.setText(settings.email)
//            binding.passwordText.setText(settings.password)
        }

        settingViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    // Override onOptionsItemSelected to handle the back button press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate back
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToEditData() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, SettingEditActivity::class.java)
            startActivity(intent)
        }
    }
}
