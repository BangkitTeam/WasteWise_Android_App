package com.team.wastewise

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.wastewise.databinding.ActivityMainBinding
import com.team.wastewise.view.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the app's action bar
        setSupportActionBar(binding.topAppBar)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_favorite,
                R.id.navigation_history,
                R.id.navigation_profile)
        )

        // Link navigation controller with the action bar and bottom navigation
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Add listener for destination changes to handle toolbar menu visibility and title
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_profile -> {
                    binding.topAppBar.title = getString(R.string.profile) // Set profile title
                }
                R.id.navigation_home -> {
                    binding.topAppBar.title = getString(R.string.home) // Set home title
                }
                R.id.navigation_favorite -> {
                    binding.topAppBar.title = getString(R.string.favorite) // Set favorite title
                }
                R.id.navigation_history -> {
                    binding.topAppBar.title = getString(R.string.history) // Set history title
                }
                R.id.navigation_upload -> {
                    binding.topAppBar.menu.clear() // Clear menu for upload fragment
                    binding.topAppBar.title = "Upload" // Clear title for upload fragment
                }
                R.id.resultFragment -> {
                    binding.topAppBar.title = "Result"
                }
                else -> {
                    binding.topAppBar.title = getString(R.string.app_name) // Default title
                }
            }
        }
    }

    // Inflate the toolbar menu from XML
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }


    // Handle clicks on toolbar menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                // Navigate to ProfileActivity, not fragment
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item) // Handle other menu clicks
        }
    }

    // Handle the "up" button in the action bar (back navigation)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}