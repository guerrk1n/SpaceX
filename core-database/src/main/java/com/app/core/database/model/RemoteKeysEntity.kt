package com.app.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    override val id: String,
    val prevKey: Int?,
    val nextKey: Int?
): BaseEntity()