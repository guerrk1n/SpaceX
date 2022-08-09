package com.app.core.data.di

import com.app.core.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRocketsRepository(
        rocketsRepository: RocketsRepositoryImpl,
    ): RocketsRepository

    @Binds
    fun bindCrewMembersRepository(
        crewMembersRepository: CrewMembersRepositoryImpl,
    ): CrewMembersRepository

    @Binds
    fun bindHistoryEventsRepository(
        historyEventsRepository: HistoryEventsRepositoryImpl,
    ): HistoryEventsRepository
}