package com.example.specime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import com.example.specime.ui.theme.AppTheme
import com.example.specime.navigations.NavigationController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Specime : ComponentActivity() {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        FirebaseApp.initializeApp(this)

        val rememberLogin = sharedPreferences.getBoolean("rememberLogin", false)
        val currentUser = FirebaseAuth.getInstance().currentUser

        val startDestination = if (currentUser != null && rememberLogin) {
            "disc"
        } else {
            "login"
        }

        //FirestoreUploader.uploadQuestions()

        setContent {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                AppTheme {
                    NavigationController(startDestination = startDestination)
                }
            }
        }
    }
}