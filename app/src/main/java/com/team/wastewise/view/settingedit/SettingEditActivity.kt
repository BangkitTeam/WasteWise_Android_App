package com.team.wastewise.view.settingedit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivitySettingEditBinding
import com.team.wastewise.pref.SessionManager

class SettingEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingEditBinding
    private lateinit var sessionManager: SessionManager
    private val viewModel: SettingEditViewModel by viewModels {
        SettingEditFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {onBackPressedDispatcher.onBackPressed()}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize SessionManager and get user token
        sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken().toString()
/*
        navigateToMainActivity()


        // Set up ViewModel observers for live data
        observeViewModel()*/

    }
}