package com.app.core.data.repository

import androidx.paging.PagingData
import com.app.core.model.HistoryEvent
import kotlinx.coroutines.flow.Flow

interface HistoryEventsRepository {

    fun getHistoryEventsStream(): Flow<PagingData<HistoryEvent>>
}