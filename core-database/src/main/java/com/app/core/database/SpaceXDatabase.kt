package com.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.core.database.dao.CrewMembersDao
import com.app.core.database.dao.HistoryEventsDao
import com.app.core.database.dao.LaunchpadsDao
import com.app.core.database.dao.RemoteKeysDao
import com.app.core.database.dao.RocketsDao
import com.app.core.database.model.CrewMemberEntity
import com.app.core.database.model.HistoryEventEntity
import com.app.core.database.model.RemoteKeysEntity
import com.app.core.database.model.launchpad.LaunchpadEntity
import com.app.core.database.model.launchpad.LaunchpadImageEntity
import com.app.core.database.model.launchpad.LaunchpadRocketCrossRefEntity
import com.app.core.database.model.rocket.RocketEntity
import com.app.core.database.model.rocket.RocketImageEntity

@Database(
    entities = [
        RocketEntity::class,
        RocketImageEntity::class,
        CrewMemberEntity::class,
        HistoryEventEntity::class,
        RemoteKeysEntity::class,
        LaunchpadEntity::class,
        LaunchpadImageEntity::class,
        LaunchpadRocketCrossRefEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun crewMembersDao(): CrewMembersDao
    abstract fun historyEventsDao(): HistoryEventsDao
    abstract fun rocketDao(): RocketsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun launchpadsDao(): LaunchpadsDao
}