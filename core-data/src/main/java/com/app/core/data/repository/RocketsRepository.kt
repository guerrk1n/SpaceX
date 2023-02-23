package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.SortType
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail
import kotlinx.coroutines.flow.Flow

interface RocketsRepository {

    fun getRocketsStream(): Flow<PagingData<Rocket>>

    suspend fun getRocketById(id: String): RocketDetail

    fun getRocketSortType(): Flow<SortType>

    suspend fun saveRocketSortType(sortType: SortType)
}