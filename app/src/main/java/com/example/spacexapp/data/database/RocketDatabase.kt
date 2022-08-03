package com.example.spacexapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacexapp.data.database.dao.RemoteKeysDao
import com.example.spacexapp.data.database.dao.RocketsDao
import com.example.spacexapp.data.database.model.RemoteKeysEntity
import com.example.spacexapp.data.database.model.RocketEntity
import com.example.spacexapp.data.database.util.StringListConverter

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