package com.app.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.spacexapp.data.local.HistoryEventsDatabase
import com.app.spacexapp.data.paging.HistoryEventsRemoteMediator
import com.app.spacexapp.data.remote.SpaceXService
import com.app.spacexapp.model.local.entities.HistoryEventEntity
import com.app.spacexapp.model.remote.mappers.HistoryEventResponseToHistoryEventEntityMapper
import com.app.spacexapp.util.Constants
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