package com.example.spacexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacexapp.data.local.dao.CrewMembersDao
import com.example.spacexapp.data.local.dao.RemoteKeysDao
import com.example.spacexapp.model.local.entities.CrewMemberEntity
import com.example.spacexapp.model.local.entities.RemoteKeysEntity
import com.example.spacexapp.data.local.util.StringListConverter

@Database(
    entities = [CrewMemberEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(StringListConverter::class)
abstract class CrewMembersDatabase : RoomDatabase() {

    abstract fun crewMembersDao(): CrewMembersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}