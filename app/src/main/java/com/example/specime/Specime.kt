package com.example.specime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import com.example.specime.ui.theme.AppTheme
import com.example.specime.navigations.NavigationController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Specime : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                AppTheme {
                    NavigationController()
                }
            }
        }
    }
}