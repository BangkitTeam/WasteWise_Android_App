package com.team.wastewise.view.settingedit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.team.wastewise.MainActivity
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivitySettingEditBinding
import com.team.wastewise.pref.SessionManager
import com.team.wastewise.data.Result
import com.team.wastewise.view.login.LoginActivity

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
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize SessionManager and get user token
        sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken().toString()

        moveToMain()

        // Set click listener for the "Save" button to initiate save logic.
        binding.btnSave.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Validate input before proceeding with save.
            if (isInputValid(username, email, password)) {
                viewModel.updateUserSettings(token, username, email, password)
            }
        }

        // Set up ViewModel observers for live data
        observeViewModel()
    }



    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.updateUser.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)

                    // Show a Toast message for success
                    Toast.makeText(this, "Edit data successful!", Toast.LENGTH_SHORT).show()

                    // Navigate to LoginActivity on successful registration.
                    val intent = Intent(this@SettingEditActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    // Show a Toast message for error
                    Toast.makeText(this, "Edit data failed: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isInputValid(name: String, email: String, password: String): Boolean {
        var isValid = true

        // Check if the name field is empty.
        if (name.isEmpty()) {
            binding.nameEditTextLayout.error = getString(R.string.empty_name)
            isValid = false
        } else {
            binding.nameEditTextLayout.error = null
        }

        // Check if the email field is empty.
        if (email.isEmpty()) {
            binding.emailEditTextLayout.error = getString(R.string.empty_email)
            isValid = false
        } else {
            binding.emailEditTextLayout.error = null
        }

        // Check if the password field is empty.
        if (password.isEmpty()) {
            binding.passwordEditTextLayout.error = getString(R.string.empty_password)
            isValid = false
        } else {
            binding.passwordEditTextLayout.error = null
        }

        return isValid
    }

    private fun showLoading(isLoading: Boolean) {
        // Show or hide the progress bar based on the loading state.
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun moveToMain() {
        binding.btnSave.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
