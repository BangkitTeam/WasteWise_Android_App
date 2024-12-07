package com.team.wastewise.view.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivityProfileBinding
import com.team.wastewise.view.login.LoginActivity
import com.team.wastewise.view.setting.SettingActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.profile)

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
}