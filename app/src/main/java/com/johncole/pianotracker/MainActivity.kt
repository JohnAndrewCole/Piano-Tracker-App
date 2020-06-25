package com.johncole.pianotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.NavHostFragment
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?
        val navController = navHostFragment!!.navController

        navView.setupWithNavController(navController)
    }
}