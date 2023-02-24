package com.app.core.database.model.launchpad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = LaunchpadImageEntity.TABLE_NAME,
    primaryKeys = [LaunchpadImageEntity.FIELD_ID, LaunchpadImageEntity.FIELD_IMAGE],
    foreignKeys = [
        ForeignKey(
            entity = LaunchpadEntity::class,
            parentColumns = [LaunchpadEntity.FIELD_ID],
            childColumns = [LaunchpadImageEntity.FIELD_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LaunchpadImageEntity(
    @ColumnInfo(FIELD_ID)
    val id: String,
    @ColumnInfo(FIELD_IMAGE)
    val image: String
) {
    companion object {
        const val TABLE_NAME = "launchpads_images_dbo"
        const val FIELD_ID = "id"
        const val FIELD_IMAGE = "image"
    }
}
