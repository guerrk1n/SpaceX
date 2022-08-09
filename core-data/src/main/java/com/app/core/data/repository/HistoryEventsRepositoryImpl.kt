package com.app.core.data.repository

import androidx.paging.*
import com.app.core.data.remotemediators.HistoryEventsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalModel
import com.app.core.model.HistoryEvent
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryEventsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
) : HistoryEventsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getHistoryEventsStream(): Flow<PagingData<HistoryEvent>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = HistoryEventsRemoteMediator(spaceXService, database),
            pagingSourceFactory = { database.historyEventsDao().getAll() }
        ).flow.map { it.map { historyEventEntity -> historyEventEntity.asExternalModel() } }
    }
}