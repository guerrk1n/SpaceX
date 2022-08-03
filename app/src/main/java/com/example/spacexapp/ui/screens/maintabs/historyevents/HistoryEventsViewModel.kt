package com.example.spacexapp.ui.screens.maintabs.historyevents

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spacexapp.data.repository.HistoryEventsRepository
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.model.local.mappers.HistoryEventEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryEventsViewModel(
    historyEventsRepository: HistoryEventsRepository,
    private val mapper: HistoryEventEntityMapper,
) : ViewModel() {

    val historyEvents: Flow<PagingData<HistoryEvent>> =
        historyEventsRepository.getHistoryEventsStream()
            .map { pagingData -> pagingData.map(mapper::map) }

}