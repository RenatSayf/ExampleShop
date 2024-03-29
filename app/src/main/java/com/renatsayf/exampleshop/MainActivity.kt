@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.exampleshop

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.renatsayf.resourses.extensions.toDeepLink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
                    R.id.profileFragment -> {
                        navigationView.apply {
                            menu.findItem(R.id.profile).isChecked = true
                            visibility = View.VISIBLE
                        }
                    }
                    R.id.tradeListFragment -> {
                        navigationView.apply {
                            menu.findItem(R.id.home).isChecked = true
                            visibility = View.VISIBLE
                        }
                    }
                    R.id.tradeDetailFragment -> {
                        navigationView.apply {
                            menu.findItem(R.id.home).isChecked = true
                            visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        navigationView.visibility = View.GONE
                    }
                }
            }
        })

        navigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                val manager = navHostFragment.childFragmentManager
                when(item.itemId) {
                    R.id.home -> {
                        item.isChecked = true
                        val fragment = manager.primaryNavigationFragment
                        val userString = fragment?.arguments?.getString("user")
                        navController.navigate("trade/$userString".toDeepLink())
                    }
                    R.id.favorite -> {
                        //item.isChecked = true
                    }
                    R.id.basket -> {
                        //item.isChecked = true
                    }
                    R.id.notification -> {
                        //item.isChecked = true
                    }
                    R.id.profile -> {
                        item.isChecked = true
                        val fragment = manager.primaryNavigationFragment
                        val userString = fragment?.arguments?.getString("user")
                        navController.navigate("profile/$userString".toDeepLink())
                    }
                }
                return false
            }
        })

    }
}



