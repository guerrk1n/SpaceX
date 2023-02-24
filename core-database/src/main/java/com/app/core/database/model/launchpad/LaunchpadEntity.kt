package com.app.core.database.model.launchpad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.database.model.BaseEntity

@Entity(tableName = LaunchpadEntity.TABLE_NAME)
data class LaunchpadEntity(
    @ColumnInfo(FIELD_ID)
    @PrimaryKey
    override val id: String,
    @ColumnInfo(FIELD_NAME)
    val name: String,
    val fullName: String,
    val status: String,
    val locality: String,
    val region: String,
    val landingAttempts: Int,
    val landingSuccesses: Int,
    val details: String,
    val timeZone: String,
    @ColumnInfo(FIELD_CREATED_AT)
    val createdAt: Long,
) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "launchpad_dbo"
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_CREATED_AT = "createdAt"
    }
}