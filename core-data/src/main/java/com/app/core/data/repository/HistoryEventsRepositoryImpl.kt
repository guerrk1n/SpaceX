package com.app.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.core.data.providers.search.SearchQueryProvider
import com.app.core.data.providers.sort.SortTypeProvider
import com.app.core.data.remotemediators.HistoryEventsRemoteMediator
import com.app.core.data.util.DataConstants
import com.app.core.database.SpaceXDatabase
import com.app.core.database.model.asExternalModel
import com.app.core.model.DataType
import com.app.core.model.HistoryEvent
import com.app.core.model.sort.HistoryEventSortType
import com.app.core.network.SpaceXService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HistoryEventsRepositoryImpl @Inject constructor(
    private val spaceXService: SpaceXService,
    private val database: SpaceXDatabase,
    private val sortTypeProvider: SortTypeProvider<HistoryEventSortType>,
    private val searchQueryProvider: SearchQueryProvider,
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
                    sortTypeProvider.getSortType()
                }
                val query = searchQueryProvider.queryMap[DataType.HISTORY_EVENTS.name] ?: ""
                when (sortType) {
                    HistoryEventSortType.NAME_ASC -> database.historyEventsDao().getAllByNameAsc(query)
                    HistoryEventSortType.NAME_DESC -> database.historyEventsDao().getAllByNameDesc(query)
                    HistoryEventSortType.DATE_ASC -> database.historyEventsDao().getAllByDateAsc(query)
                    HistoryEventSortType.DATE_DESC -> database.historyEventsDao().getAllByDateDesc(query)
                }
            }
        ).flow.map { it.map { historyEventEntity -> historyEventEntity.asExternalModel() } }
    }

    override fun getHistoryEventSortType(): Flow<HistoryEventSortType> {
        return sortTypeProvider.getSortTypeFlow()
    }

    override suspend fun saveHistoryEventSortType(sortType: HistoryEventSortType) {
        sortTypeProvider.saveSortType(sortType)
    }

    override suspend fun saveSearchQuery(query: String) {
        searchQueryProvider.queryMap[DataType.HISTORY_EVENTS.name] = query
    }
}