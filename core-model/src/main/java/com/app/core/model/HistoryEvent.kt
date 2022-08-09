package com.app.core.model

data class HistoryEvent(
    val id: String,
    val link: String?,
    val title: String,
    val date: Long,
    val details: String,
)
