package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail
import kotlinx.coroutines.flow.Flow

interface LaunchpadsRepository {

    fun getLaunchpadsStream(): Flow<PagingData<Launchpad>>

    suspend fun getLaunchpadById(id: String): LaunchpadDetail
}