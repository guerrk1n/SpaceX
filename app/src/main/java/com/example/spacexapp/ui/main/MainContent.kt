package com.example.spacexapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacexapp.R
import com.example.spacexapp.ui.historyevents.HistoryEventsScreen
import com.example.spacexapp.ui.theme.googleSansFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainContent() {
    val systemUiComposable = rememberSystemUiController()
    SideEffect { systemUiComposable.setSystemBarsColor(color = Color.Black) }

    Scaffold(
        topBar = { Toolbar() },
        backgroundColor = Color.Black,
        content = {
            Box(modifier = Modifier.padding(top = 50.dp)) { HistoryEventsScreen() }
        }
    )
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = googleSansFamily,
                fontWeight = FontWeight.Medium,
            )
        },
        backgroundColor = Color.Black,
    )
}

@Preview
@Composable
fun PreviewMainContent(){
    MainContent()
}
