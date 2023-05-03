package com.app.core.data.di

import com.app.core.data.providers.sort.CrewMemberSortTypeProvider
import com.app.core.data.providers.sort.HistoryEventsSortTypeProvider
import com.app.core.data.providers.sort.LaunchpadSortTypeProvider
import com.app.core.data.providers.sort.RocketsSortTypeProvider
import com.app.core.data.providers.sort.SortTypeProvider
import com.app.core.data.repository.CrewMembersRepository
import com.app.core.data.repository.CrewMembersRepositoryImpl
import com.app.core.data.repository.HistoryEventsRepository
import com.app.core.data.repository.HistoryEventsRepositoryImpl
import com.app.core.data.repository.LaunchpadsRepository
import com.app.core.data.repository.LaunchpadsRepositoryImpl
import com.app.core.data.repository.RocketsRepository
import com.app.core.data.repository.RocketsRepositoryImpl
import com.app.core.model.sort.HistoryEventSortType
import com.app.core.model.sort.CrewMemberSortType
import com.app.core.model.sort.LaunchpadSortType
import com.app.core.model.sort.RocketSortType
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

    @Binds
    fun bindLaunchpadsRepository(
        launchpadsRepository: LaunchpadsRepositoryImpl,
    ): LaunchpadsRepository

    @Binds
    fun bindHistoryEventsSortTypeProvider(
        sortTypeProvider: HistoryEventsSortTypeProvider,
    ): SortTypeProvider<HistoryEventSortType>

    @Binds
    fun bindRocketsSortTypeProvider(
        sortTypeProvider: RocketsSortTypeProvider,
    ): SortTypeProvider<RocketSortType>

    @Binds
    fun bindCrewMembersSortTypeProvider(
        sortTypeProvider: CrewMemberSortTypeProvider,
    ): SortTypeProvider<CrewMemberSortType>

    @Binds
    fun bindLaunchpadsSortTypeProvider(
        sortTypeProvider: LaunchpadSortTypeProvider,
    ): SortTypeProvider<LaunchpadSortType>
}