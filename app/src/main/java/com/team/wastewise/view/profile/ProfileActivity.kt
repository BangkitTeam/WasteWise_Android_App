package com.team.wastewise.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivityProfileBinding

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
    }
}