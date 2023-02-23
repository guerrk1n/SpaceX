package com.app.core.data.preference

import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow

interface UserPreference {
    fun getRocketSortType(): Flow<SortType>

    suspend fun saveRocketSortType(sortType: SortType)

    fun getCrewMembersSortType(): Flow<SortType>

    suspend fun saveCrewMembersSortType(sortType: SortType)

    fun getHistoryEventsSortType(): Flow<SortType>

    suspend fun saveHistoryEventsSortType(sortType: SortType)

    fun getLaunchpadSortType(): Flow<SortType>

    suspend fun saveLaunchpadSortType(sortType: SortType)
}