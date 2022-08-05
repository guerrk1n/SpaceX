package com.app.spacexapp.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_event_dbo")
data class HistoryEventEntity(
    @PrimaryKey
    val id: String,
    val link: String?,
    val title: String,
    val date: Long,
    val details: String,
    val createdAt: Long,
)