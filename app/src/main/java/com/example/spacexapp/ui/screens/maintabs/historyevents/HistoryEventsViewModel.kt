package com.example.spacexapp.ui.screens.maintabs.historyevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.ui.screens.maintabs.BasePagingSource
import com.example.spacexapp.ui.screens.maintabs.MappedDataWithPagingInfo
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class HistoryEventsViewModel(
    private val spaceXService: SpaceXService,
    private val mapper: HistoryEventMapper,
) : ViewModel() {

    private val pager: Pager<Int, HistoryEvent> =
        Pager(config = PagingConfig(Constants.PAGE_SIZE), pagingSourceFactory = ::initPagingSource)

    val historyEvents: Flow<PagingData<HistoryEvent>> = pager.flow.cachedIn(viewModelScope)

    private fun initPagingSource() = BasePagingSource {
        viewModelScope.async {
            val response = spaceXService.getHistoryEvents(it)
            val historyEvents = response.historyEvents.map(mapper::map)
            val totalPages = response.totalPages
            val page = response.page
            MappedDataWithPagingInfo(historyEvents, totalPages, page)
        }
    }
}