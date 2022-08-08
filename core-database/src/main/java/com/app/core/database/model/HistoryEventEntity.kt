package com.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.HistoryEvent

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

fun HistoryEventEntity.asExternalModel() = HistoryEvent(
    id = id,
    link = link,
    title = title,
    date = date,
    details = details,
)