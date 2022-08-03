package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacexapp.data.local.RocketDatabase
import com.example.spacexapp.data.paging.RocketsRemoteMediator
import com.example.spacexapp.data.remote.SpaceXService
import com.example.spacexapp.model.local.entities.RocketEntity
import com.example.spacexapp.model.remote.mappers.RocketResponseMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class RocketsRepository(
    private val spaceXService: SpaceXService,
    private val database: RocketDatabase,
    private val mapper: RocketResponseMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getRocketsStream(): Flow<PagingData<RocketEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RocketsRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.rocketDao().getAllItems() }
        ).flow
    }
}