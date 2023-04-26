package com.hebs.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hebs.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun getNavigationView() = binding.bottomNavView

    private fun currentNavController() = findNavController(R.id.nav_host_fragment_activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_genres
            )
        )
        setupActionBarWithNavController(currentNavController(), appBarConfiguration)
        getNavigationView().setupWithNavController(currentNavController())
    }
}