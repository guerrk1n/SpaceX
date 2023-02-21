package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.remotemediators.LaunchpadsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalDetailModel
import com.app.core.database.model.asExternalModel
import com.app.core.model.Launchpad
import com.app.core.model.LaunchpadDetail
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LaunchpadsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
) : LaunchpadsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getLaunchpadsStream(): Flow<PagingData<Launchpad>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = LaunchpadsRemoteMediator(spaceXService, database),
            pagingSourceFactory = { database.launchpadsDao().getAll() }
        ).flow.map {
            it.map { launchpadEntity -> launchpadEntity.asExternalModel() }
        }
    }

    override suspend fun getLaunchpadById(id: String): LaunchpadDetail {
        return database.launchpadsDao().getItemById(id).asExternalDetailModel()
    }
}