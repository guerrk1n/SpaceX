package com.example.spacexapp.ui.historyevents

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spacexapp.api.HistoryEventsService
import com.example.spacexapp.data.Options
import com.example.spacexapp.data.QueryBody
import com.example.spacexapp.ui.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.ui.historyevents.historyevent.HistoryEventMapper
import com.example.spacexapp.util.Constants

class HistoryEventsPagingSource(
    private val historyEventsService: HistoryEventsService,
    private val mapper: HistoryEventMapper,
) : PagingSource<Int, HistoryEvent>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryEvent> {
        return try {
            val nextPage = params.key ?: 1
            val options = Options(nextPage, Constants.PAGE_SIZE)
            val queryBody = QueryBody(options)
            val historyEventsResponse = historyEventsService.getHistoryEvents(queryBody)
            val historyEvents = historyEventsResponse.historyEvents.map(mapper::map)
            LoadResult.Page(
                historyEvents,
                if (nextPage == 1) null else nextPage - 1,
                if (nextPage >= historyEventsResponse.totalPages) null else historyEventsResponse.page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, HistoryEvent>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }
}