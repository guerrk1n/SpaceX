package com.app.core.database.model.rocket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = RocketImageEntity.TABLE_NAME,
    primaryKeys = [RocketImageEntity.FIELD_ID, RocketImageEntity.FIELD_IMAGE],
    foreignKeys = [
        ForeignKey(
            entity = RocketEntity::class,
            parentColumns = [RocketEntity.FIELD_ID],
            childColumns = [RocketImageEntity.FIELD_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RocketImageEntity(
    @ColumnInfo(FIELD_ID)
    val id: String,
    @ColumnInfo(FIELD_IMAGE)
    val image: String
) {
    companion object {
        const val TABLE_NAME = "rockets_images_dbo"
        const val FIELD_ID = "id"
        const val FIELD_IMAGE = "image"
    }
}