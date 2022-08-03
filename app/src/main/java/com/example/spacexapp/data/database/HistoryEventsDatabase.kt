package com.example.spacexapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spacexapp.data.database.dao.HistoryEventsDao
import com.example.spacexapp.data.database.dao.RemoteKeysDao
import com.example.spacexapp.data.database.model.HistoryEventEntity
import com.example.spacexapp.data.database.model.RemoteKeysEntity

@Database(
    entities = [HistoryEventEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class HistoryEventsDatabase : RoomDatabase() {

    abstract fun historyEventsDao(): HistoryEventsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}