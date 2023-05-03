package com.app.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.model.HistoryEvent

@Entity(tableName = HistoryEventEntity.TABLE_NAME)
data class HistoryEventEntity(
    @PrimaryKey
    @ColumnInfo(FIELD_ID)
    override val id: String,
    val link: String?,
    @ColumnInfo(FIELD_TITLE)
    val title: String,
    @ColumnInfo(FIELD_DATE)
    val date: Long,
    val details: String,
    val createdAt: Long,
): BaseEntity() {
    companion object {
        const val TABLE_NAME = "history_event_dbo"
        const val FIELD_ID = "id"
        const val FIELD_TITLE = "title"
        const val FIELD_DATE = "date"
    }
}

fun HistoryEventEntity.asExternalModel() = HistoryEvent(
    id = id,
    link = link,
    title = title,
    date = date,
    details = details,
)