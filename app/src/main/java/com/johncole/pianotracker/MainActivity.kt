package com.johncole.pianotracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johncole.pianotracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navView: BottomNavigationView = binding.navView

        /**
         * This is currently (as of 25 June 2020) the only way to use a navHostFragment with a fragment widget.
         * See: https://stackoverflow.com/questions/59275009/fragmentcontainerview-using-findnavcontroller
         * and: https://issuetracker.google.com/issues/142847973
         */
        val navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?)!!.navController

        // setting the Bottom navigation visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.sessionFragment || destination.id == R.id.goalDialogFragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.sessionListFragment,
                R.id.statsFragment,
                R.id.settingsFragment
            )
            .build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return this.findNavController(R.id.navHostFragment)
            .navigateUp() || super.onSupportNavigateUp()
    }
}
