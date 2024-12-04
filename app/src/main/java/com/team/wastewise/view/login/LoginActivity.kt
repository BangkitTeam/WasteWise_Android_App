package com.team.wastewise.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.team.wastewise.MainActivity
import com.team.wastewise.R
import com.team.wastewise.databinding.ActivityLoginBinding
import com.team.wastewise.pref.SessionManager
import com.team.wastewise.view.register.RegisterActivity
import kotlinx.coroutines.launch
import com.team.wastewise.data.Result


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    // Using ViewModelProvider to get an instance of LoginViewModel.
    private val loginViewModel: LoginViewModel by viewModels {
        LoginFactory.getInstance(this) // Factory provides dependency injection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sessionManager = SessionManager(this) // SessionManager for managing user sessions



        moveToRegister()

        //TODO: Login (Logic sementara)
        moveToHome()

        setupClickListeners() // Sets up UI event listeners
        observeViewModel() // Observes ViewModel LiveData for updates

    }

    private fun moveToRegister() {

    }

    private fun moveToHome() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupClickListeners() {
        // Navigates to Register screen.
        binding.registerNow.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Validates input and triggers login process.
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (isInputValid(email, password)) {
                loginViewModel.login(email, password)
            }
        }
    }


    private fun observeViewModel() {
        // Observes loading state and toggles progress bar visibility.
        loginViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Observes login result and handles success or error scenarios.
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    val token = result.data.token
                    lifecycleScope.launch {
                        sessionManager.saveAuthToken(token) // Saves auth token in session
                    }

                    // Show a Toast message for success
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    navigateToMainActivity() // Redirects to MainActivity
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe registration result and handle success/error scenarios.
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    val token = result.data.token
                    lifecycleScope.launch {
                        sessionManager.saveAuthToken(token) // Saves auth token in session
                    }
                    // Show a Toast message for success
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Redirects to MainActivity
                    navigateToMainActivity()
                }
                is Result.Error -> {
                    showLoading(false)
                    // Show a Toast message for error
                    Toast.makeText(this, "Registration failed: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Ends current activity to remove it from the back stack
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Validates user input for email and password.
    private fun isInputValid(email: String, password: String): Boolean {
        var isValid = true
        if (email.isEmpty()) {
            binding.emailEditTextLayout.error = getString(R.string.empty_email)
            isValid = false
        } else {
            binding.emailEditTextLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordEditTextLayout.error = getString(R.string.empty_password)
            isValid = false
        } else {
            binding.passwordEditTextLayout.error = null
        }

        return isValid
    }
}