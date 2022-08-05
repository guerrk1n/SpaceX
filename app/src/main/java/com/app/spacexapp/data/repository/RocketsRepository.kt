package com.app.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.spacexapp.data.local.RocketDatabase
import com.app.spacexapp.data.paging.RocketsRemoteMediator
import com.app.spacexapp.data.remote.SpaceXService
import com.app.spacexapp.model.local.entities.RocketEntity
import com.app.spacexapp.model.remote.mappers.RocketResponseToRocketEntityMapper
import com.app.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class RocketsRepository(
    private val spaceXService: SpaceXService,
    private val database: RocketDatabase,
    private val mapper: RocketResponseToRocketEntityMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getRocketsStream(): Flow<PagingData<RocketEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RocketsRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.rocketDao().getAll() }
        ).flow
    }

    suspend fun getRocketById(id: String): RocketEntity {
        return database.rocketDao().getItemById(id)
    }
}