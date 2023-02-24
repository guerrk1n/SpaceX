package com.app.core.database.model.rocket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.core.database.model.BaseEntity

@Entity(tableName = RocketEntity.TABLE_NAME)
data class RocketEntity(
    @PrimaryKey
    @ColumnInfo(FIELD_ID)
    override val id: String,
    @ColumnInfo(FIELD_NAME)
    val name: String,
    val active: Boolean,
    val firstFlight: String,
    val wikipedia: String,
    val description: String,
    @ColumnInfo(FIELD_CREATED_AT)
    val createdAt: Long
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "rocket_dbo"
        const val FIELD_ID = "id"
        const val FIELD_NAME = "name"
        const val FIELD_CREATED_AT = "createdAt"
    }
}