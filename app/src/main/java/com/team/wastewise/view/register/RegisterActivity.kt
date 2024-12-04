package com.team.wastewise.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivityRegisterBinding
import com.team.wastewise.view.login.LoginActivity
import com.team.wastewise.data.Result

class RegisterActivity : AppCompatActivity() {
     private lateinit var binding: ActivityRegisterBinding

    // Use the ViewModelFactory to provide an instance of RegisterViewModel.
    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        moveToLogin()

        // Set click listener for the "Register" button to initiate registration logic.
        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            // Validate input before proceeding with registration.
            if (isInputValid(username, email, password)) {
                registerViewModel.register(username, email, password)
            }
        }

        // Observe changes from the ViewModel.
        observeViewModel()
    }

    private fun observeViewModel() {
        // Observe loading state to display/hide the loading indicator.
        registerViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

// Observe registration result and handle success/error scenarios.
        registerViewModel.registerResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    // Show a Toast message for success
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                    // Navigate to LoginActivity on successful registration.
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    showLoading(false)
                    // Show a Toast message for error
                    Toast.makeText(this, "Registration failed: ${result.error}", Toast.LENGTH_SHORT).show()
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


    private fun moveToLogin() {
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}