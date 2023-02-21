package com.app.core.database.di

import com.app.core.database.SpaceXDatabase
import com.app.core.database.dao.CrewMembersDao
import com.app.core.database.dao.HistoryEventsDao
import com.app.core.database.dao.LaunchpadsDao
import com.app.core.database.dao.RemoteKeysDao
import com.app.core.database.dao.RocketsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesCrewMembersDao(database: SpaceXDatabase): CrewMembersDao = database.crewMembersDao()

    @Provides
    fun providesHistoryEventDao(database: SpaceXDatabase): HistoryEventsDao =
        database.historyEventsDao()

    @Provides
    fun providesRocketsDao(database: SpaceXDatabase): RocketsDao = database.rocketDao()

    @Provides
    fun providesRemoteKeysDao(database: SpaceXDatabase): RemoteKeysDao = database.remoteKeysDao()

    @Provides
    fun providesLaunchpadsDao(database: SpaceXDatabase): LaunchpadsDao = database.launchpadsDao()
}