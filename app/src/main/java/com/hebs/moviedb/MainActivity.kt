package com.hebs.moviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hebs.moviedb.databinding.ActivityMainBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.presentation.detail.DetailFragmentArgs
import com.hebs.moviedb.presentation.detail.DetailListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DetailListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun getNavigationView() = binding.bottomNavView

    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_fragment,
                R.id.navigation_search_fragment,
                R.id.navigation_genres_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        getNavigationView().setupWithNavController(navController)
    }

    override fun showDetail(resource: Resource) {
        val arguments = DetailFragmentArgs(
            resource = resource
        ).toBundle()
        navController.navigate(R.id.navigation_detail_fragment, arguments)
    }
}