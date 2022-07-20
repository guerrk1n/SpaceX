package com.example.spacexapp.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.spacexapp.ui.historyevents.HistoryEventsScreen

@Composable
fun MainContent(isDarkTheme: MutableState<Boolean>) {
    HistoryEventsScreen()
}