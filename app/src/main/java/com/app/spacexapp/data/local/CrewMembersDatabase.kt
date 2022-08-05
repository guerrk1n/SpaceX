package com.app.spacexapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.spacexapp.data.local.dao.CrewMembersDao
import com.app.spacexapp.data.local.dao.RemoteKeysDao
import com.app.spacexapp.data.local.util.StringListConverter
import com.app.spacexapp.model.local.entities.CrewMemberEntity
import com.app.spacexapp.model.local.entities.RemoteKeysEntity

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