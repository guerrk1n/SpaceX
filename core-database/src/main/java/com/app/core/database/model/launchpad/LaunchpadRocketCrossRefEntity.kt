package com.app.core.database.model.launchpad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.app.core.database.model.rocket.RocketEntity

@Entity(
    tableName = LaunchpadRocketCrossRefEntity.TABLE_NAME,
    primaryKeys = [LaunchpadRocketCrossRefEntity.FIELD_LAUNCHPAD_ID, LaunchpadRocketCrossRefEntity.FIELD_ROCKET_ID],
    foreignKeys = [
        ForeignKey(
            entity = LaunchpadEntity::class,
            parentColumns = [LaunchpadEntity.FIELD_ID],
            childColumns = [LaunchpadRocketCrossRefEntity.FIELD_LAUNCHPAD_ID],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = RocketEntity::class,
            parentColumns = [RocketEntity.FIELD_ID],
            childColumns = [LaunchpadRocketCrossRefEntity.FIELD_ROCKET_ID],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = [LaunchpadRocketCrossRefEntity.FIELD_ROCKET_ID]),
        Index(value = [LaunchpadRocketCrossRefEntity.FIELD_LAUNCHPAD_ID]),
    ]
)
data class LaunchpadRocketCrossRefEntity(
    @ColumnInfo(FIELD_LAUNCHPAD_ID)
    val launchpadId: String,
    @ColumnInfo(FIELD_ROCKET_ID)
    val rocketId: String,
) {
    companion object {
        const val TABLE_NAME = "launchpads_rockets_dbo"
        const val FIELD_LAUNCHPAD_ID = "id"
        const val FIELD_ROCKET_ID = "rocketId"
    }
}