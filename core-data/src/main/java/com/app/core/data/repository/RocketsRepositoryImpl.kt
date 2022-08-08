package com.app.core.data.repository

import androidx.paging.*
import com.app.core.common.Constants
import com.app.core.data.remotemediators.RocketsRemoteMediator
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalDetailModel
import com.app.core.database.model.asExternalModel
import com.app.core.model.Rocket
import com.app.core.model.RocketDetail
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RocketsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
) : RocketsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRocketsStream(): Flow<PagingData<Rocket>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RocketsRemoteMediator(spaceXService, database),
            pagingSourceFactory = { database.rocketDao().getAll() }
        ).flow.map {
            it.map { rocketEntity -> rocketEntity.asExternalModel() }
        }
    }

    override suspend fun getRocketById(id: String): RocketDetail {
        return database.rocketDao().getItemById(id).asExternalDetailModel()
    }
}