package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.HistoryEvent
import com.app.core.model.sort.HistoryEventSortType
import kotlinx.coroutines.flow.Flow

interface HistoryEventsRepository {

    fun getHistoryEventsStream(): Flow<PagingData<HistoryEvent>>

    fun getHistoryEventSortType(): Flow<HistoryEventSortType>

    suspend fun saveHistoryEventSortType(sortType: HistoryEventSortType)

    suspend fun saveSearchQuery(query: String)
}