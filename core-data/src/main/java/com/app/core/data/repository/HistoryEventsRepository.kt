package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.HistoryEvent
import com.app.core.model.SortType
import kotlinx.coroutines.flow.Flow

interface HistoryEventsRepository {

    fun getHistoryEventsStream(): Flow<PagingData<HistoryEvent>>

    fun getHistoryEventSortType(): Flow<SortType>

    suspend fun saveHistoryEventSortType(sortType: SortType)
}