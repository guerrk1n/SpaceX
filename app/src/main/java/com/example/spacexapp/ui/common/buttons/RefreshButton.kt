package com.example.spacexapp.ui.common.buttons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.spacexapp.R

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