package com.example.spacexapp.ui.common.error

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacexapp.R
import com.example.spacexapp.ui.common.buttons.RefreshButton

@Composable
fun ErrorColumn(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    @StringRes textRes: Int = R.string.spacex_app_error,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 15.dp),
            text = stringResource(textRes),
            color = color
        )
        RefreshButton(
            onClick = onClick,
            color
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorColumnPreview() {
    ErrorColumn() {}
}
