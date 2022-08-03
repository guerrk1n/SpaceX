package com.example.spacexapp.ui.common.loading

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacexapp.R

@Composable
fun LoadingColumn(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.spacex_app_loading),
            color = color
        )
        CircularProgressIndicator(modifier = Modifier
            .size(40.dp)
            .padding(top = 16.dp),
            color = color
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingColumnPreview() {
    LoadingColumn()
}
