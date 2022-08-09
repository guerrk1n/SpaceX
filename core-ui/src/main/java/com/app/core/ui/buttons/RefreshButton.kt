package com.app.core.ui.buttons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.app.core.ui.R

@Composable
fun RefreshButton(onClick: () -> Unit, color: Color = Color.Black) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.ic_refresh),
            contentDescription = stringResource(R.string.spacex_app_refresh),
            tint = color
        )
    }
}