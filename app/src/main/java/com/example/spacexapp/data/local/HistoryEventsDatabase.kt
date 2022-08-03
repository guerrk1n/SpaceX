package com.example.spacexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spacexapp.data.local.dao.HistoryEventsDao
import com.example.spacexapp.data.local.dao.RemoteKeysDao
import com.example.spacexapp.model.local.entities.HistoryEventEntity
import com.example.spacexapp.model.local.entities.RemoteKeysEntity

@Database(
    entities = [HistoryEventEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class HistoryEventsDatabase : RoomDatabase() {

    abstract fun historyEventsDao(): HistoryEventsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}