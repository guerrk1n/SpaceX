package com.app.core.data.preference

import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow

interface UserPreference {
    fun getRocketSortType(): Flow<SortType>

    suspend fun saveRocketSortType(sortType: SortType)
}