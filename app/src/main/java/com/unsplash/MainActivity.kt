package com.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import java.security.AccessControlContext

class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
                || navController.navigateUp(appBarConfiguration)
    }
}