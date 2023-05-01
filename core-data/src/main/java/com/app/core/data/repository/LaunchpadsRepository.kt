package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail
import com.app.core.model.sort.LaunchpadSortType
import kotlinx.coroutines.flow.Flow

interface LaunchpadsRepository {

    fun getLaunchpadsStream(): Flow<PagingData<Launchpad>>

    suspend fun getLaunchpadById(id: String): LaunchpadDetail

    fun getLaunchpadSortType(): Flow<LaunchpadSortType>

    suspend fun saveLaunchpadSortType(sortType: LaunchpadSortType)
}