package com.example.runningtracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.runningtracker.R
import com.example.runningtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController
            .addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _
                ->  when(destination.id) {
                    R.id.runningFragment, R.id.statisticsFragment, R.id.settingFragment
                    -> binding.bottomNavigationView.visibility = View.VISIBLE
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            })

    }
}