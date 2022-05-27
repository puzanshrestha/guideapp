package com.project.guideapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.project.guideapp.R
import com.project.guideapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val reselectedDestinationId = item.itemId
            navHostFragment.findNavController()
                .popBackStack(reselectedDestinationId, inclusive = false)
            navHostFragment.findNavController().navigate(item.itemId)
            true
        }
    }

    fun setStreetViewBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.menu_street_art
    }
}