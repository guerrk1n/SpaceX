package com.app.core.data.repository

import androidx.paging.*
import com.app.core.data.providers.DataType
import com.app.core.data.providers.SortTypeProvider
import com.app.core.data.remotemediators.HistoryEventsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalModel
import com.app.core.model.HistoryEvent
import com.app.core.model.SortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HistoryEventsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider,
) : HistoryEventsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getHistoryEventsStream(): Flow<PagingData<HistoryEvent>> {
        return Pager(
            config = PagingConfig(
                pageSize = DataConstants.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = HistoryEventsRemoteMediator(spaceXService, database, sortTypeProvider),
            pagingSourceFactory = {
                val sortType = runBlocking { // todo
                    sortTypeProvider.getSortType(DataType.HistoryEvents)
                }
                when (sortType) {
                    SortType.NAME_ASC -> database.historyEventsDao().getAllAsc()
                    SortType.NAME_DESC -> database.historyEventsDao().getAllDesc()
                }
            }
        ).flow.map { it.map { historyEventEntity -> historyEventEntity.asExternalModel() } }
    }

    override fun getHistoryEventSortType(): Flow<SortType> {
        return sortTypeProvider.getSortTypeFlow(DataType.HistoryEvents)
    }

    override suspend fun saveHistoryEventSortType(sortType: SortType) {
        sortTypeProvider.saveSortType(sortType, DataType.HistoryEvents)
    }
}