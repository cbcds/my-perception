package com.cbcds.myperception.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cbcds.myperception.R
import com.cbcds.myperception.models.UserViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var navController: NavController

    companion object {
        private const val RC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Temporary disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Bottom navigation bar setup
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavView.setupWithNavController(navController)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_global_stats -> {
                    if (userViewModel.authState.value == UserViewModel.AuthState.AUTHENTICATED) {
                        navController.navigate(R.id.tab_global_stats)
                    } else {
                        navController.navigate(R.id.tab_auth)
                    }
                }
                else -> NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }

        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (userViewModel.authState.value) {
            UserViewModel.AuthState.AUTHENTICATED -> {
                menu?.findItem(R.id.action_sign_in)?.isVisible = false
                menu?.findItem(R.id.action_sign_out)?.isVisible = true
            }
            UserViewModel.AuthState.UNAUTHENTICATED -> {
                menu?.findItem(R.id.action_sign_in)?.isVisible = true
                menu?.findItem(R.id.action_sign_out)?.isVisible = false
            }
        }
        super.onPrepareOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_in -> launchSignInFlow()
            R.id.action_sign_out -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Aaa", "Here")

        when (requestCode) {
            RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}