package com.example.spacexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacexapp.data.local.dao.RemoteKeysDao
import com.example.spacexapp.data.local.dao.RocketsDao
import com.example.spacexapp.model.local.entities.RemoteKeysEntity
import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.data.local.util.StringListConverter

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