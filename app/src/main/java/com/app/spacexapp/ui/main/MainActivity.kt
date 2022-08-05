package com.app.spacexapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.app.spacexapp.navigation.AppNavigation
import com.app.core.designsystem.theme.SpaceXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderUi()
    }

    private fun renderUi() = setContent {
        val systemTheme = isSystemInDarkTheme()
        val isDarkTheme = remember { mutableStateOf(systemTheme) }
        SpaceXTheme(isDarkTheme = isDarkTheme.value) {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}