package com.example.spacexapp.ui.historyevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spacexapp.api.HistoryEventsService
import com.example.spacexapp.ui.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.ui.historyevents.historyevent.HistoryEventMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.flow.Flow

class HistoryEventsViewModel(
    private val historyEventsService: HistoryEventsService,
    private val mapper: HistoryEventMapper,
) : ViewModel() {

    private val pager: Pager<Int, HistoryEvent> =
        Pager(config = PagingConfig(Constants.PAGE_SIZE), pagingSourceFactory = ::initPagingSource)

    val historyEvents: Flow<PagingData<HistoryEvent>> = pager.flow.cachedIn(viewModelScope)

    private fun initPagingSource() = HistoryEventsPagingSource(historyEventsService, mapper)

}