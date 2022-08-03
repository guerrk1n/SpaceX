package com.example.spacexapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.data.database.HistoryEventsDatabase
import com.example.spacexapp.data.database.model.HistoryEventEntity
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventResponseMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class HistoryEventsRepository(
    private val spaceXService: SpaceXService,
    private val database: HistoryEventsDatabase,
    private val mapper: HistoryEventResponseMapper,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getHistoryEventsStream(): Flow<PagingData<HistoryEventEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = HistoryEventsRemoteMediator(spaceXService, database, mapper),
            pagingSourceFactory = { database.historyEventsDao().getAllItems() }
        ).flow
    }
}