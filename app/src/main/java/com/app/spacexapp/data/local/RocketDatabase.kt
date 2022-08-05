package com.app.spacexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.spacexapp.data.local.dao.RemoteKeysDao
import com.app.spacexapp.data.local.dao.RocketsDao
import com.app.spacexapp.data.local.util.StringListConverter
import com.app.spacexapp.model.local.entities.RemoteKeysEntity
import com.app.spacexapp.model.local.entities.RocketEntity

@Database(
    entities = [RocketEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(StringListConverter::class)
abstract class RocketDatabase : RoomDatabase() {

    abstract fun rocketDao(): RocketsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}