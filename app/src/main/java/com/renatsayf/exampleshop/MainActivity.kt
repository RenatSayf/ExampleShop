@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.exampleshop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_nav).apply {
            setupWithNavController(navController)
        }

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.id) {
                    com.renatsayf.profile.R.navigation.profile_nav_graph -> {
                        navigationView.visibility = View.VISIBLE
                    }
                    com.renatsayf.trade.R.navigation.trade_nav_graph -> {
                        navigationView.visibility = View.VISIBLE
                    }
                    else -> {
                        navigationView.visibility = View.GONE
                    }
                }
            }
        })

    }
}



