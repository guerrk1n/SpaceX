package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail
import com.app.core.model.sort.RocketSortType
import kotlinx.coroutines.flow.Flow

interface RocketsRepository {

    fun getRocketsStream(): Flow<PagingData<Rocket>>

    suspend fun getRocketById(id: String): RocketDetail

    fun getRocketSortType(): Flow<RocketSortType>

    suspend fun saveRocketSortType(sortType: RocketSortType)
}