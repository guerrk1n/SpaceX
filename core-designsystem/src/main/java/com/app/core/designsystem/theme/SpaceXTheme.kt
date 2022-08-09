package com.app.core.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SpaceXTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = themeColors,
        typography = typography,
        content = content,
    )
}