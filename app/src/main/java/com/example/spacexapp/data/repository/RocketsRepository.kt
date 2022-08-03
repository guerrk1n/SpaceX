package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.data.database.RocketDatabase
import com.example.spacexapp.data.database.model.RocketEntity
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketResponseMapper
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