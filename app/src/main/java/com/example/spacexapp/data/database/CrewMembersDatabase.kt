package com.example.spacexapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacexapp.data.database.dao.CrewMembersDao
import com.example.spacexapp.data.database.dao.RemoteKeysDao
import com.example.spacexapp.data.database.model.CrewMemberEntity
import com.example.spacexapp.data.database.model.RemoteKeysEntity
import com.example.spacexapp.data.database.util.StringListConverter

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