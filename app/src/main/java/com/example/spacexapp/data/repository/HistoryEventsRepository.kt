package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacexapp.data.local.HistoryEventsDatabase
import com.example.spacexapp.data.paging.HistoryEventsRemoteMediator
import com.example.spacexapp.data.remote.SpaceXService
import com.example.spacexapp.model.local.entities.HistoryEventEntity
import com.example.spacexapp.model.remote.mappers.HistoryEventResponseToHistoryEventEntityMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class HistoryEventsRepository(
    private val spaceXService: SpaceXService,
    private val database: HistoryEventsDatabase,
    private val mapper: HistoryEventResponseToHistoryEventEntityMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getHistoryEventsStream(): Flow<PagingData<HistoryEventEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = HistoryEventsRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.historyEventsDao().getAll() }
        ).flow
    }
}