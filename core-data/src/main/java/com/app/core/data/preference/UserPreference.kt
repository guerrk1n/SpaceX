package com.app.core.data.preference

import com.app.core.model.sort.HistoryEventSortType
import com.app.core.model.sort.CrewMemberSortType
import com.app.core.model.sort.LaunchpadSortType
import com.app.core.model.sort.RocketSortType
import kotlinx.coroutines.flow.Flow

interface UserPreference {
    fun getRocketSortType(): Flow<RocketSortType>

    suspend fun saveRocketSortType(sortType: RocketSortType)

    fun getCrewMembersSortType(): Flow<CrewMemberSortType>

    suspend fun saveCrewMembersSortType(sortType: CrewMemberSortType)

    fun getHistoryEventsSortType(): Flow<HistoryEventSortType>

    suspend fun saveHistoryEventsSortType(sortType: HistoryEventSortType)

    fun getLaunchpadSortType(): Flow<LaunchpadSortType>

    suspend fun saveLaunchpadSortType(sortType: LaunchpadSortType)
}