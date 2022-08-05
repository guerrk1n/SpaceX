package com.app.spacexapp.ui.screens.maintabs.historyevents.historyevent

data class HistoryEvent(
    val id: String,
    val link: String?,
    val title: String,
    val date: Long,
    val details: String,
)
