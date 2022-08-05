package com.example.spacexapp.ui.screens.maintabs.historyevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.spacexapp.R
import com.example.spacexapp.ui.common.error.ErrorColumn
import com.example.spacexapp.ui.common.lazylists.ErrorItem
import com.example.spacexapp.ui.common.lazylists.LoadingItem
import com.example.spacexapp.ui.common.loading.LoadingColumn
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEvent
import com.example.spacexapp.ui.screens.maintabs.historyevents.historyevent.HistoryEventCard
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel
import java.net.UnknownHostException

@Composable
fun HistoryEventsTab(viewModel: HistoryEventsViewModel = getViewModel()) {
    val historyEvents = viewModel.historyEvents.collectAsLazyPagingItems()

    HistoryEventContent(historyEvents)
}

@Composable
private fun HistoryEventContent(historyEvents: LazyPagingItems<HistoryEvent>) {
    when (val refreshLoadState = historyEvents.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (historyEvents.itemCount > 0) {
                LazyHistoryEventsColumn(historyEvents)
                return
            }
            val isInternetError = refreshLoadState.error is UnknownHostException
            if (isInternetError)
                ErrorColumn(
                    textRes = R.string.spacex_app_error_internet,
                    onClick = { historyEvents.refresh() }
                )
            else
                ErrorColumn(onClick = { historyEvents.refresh() })
        }
        else -> LazyHistoryEventsColumn(historyEvents)
    }
}

@Composable
private fun LazyHistoryEventsColumn(historyEvents: LazyPagingItems<HistoryEvent>) {
    LazyColumn(verticalArrangement = Arrangement.Top) {
        items(historyEvents) { item ->
            item?.let { HistoryEventCard(it) }
        }

        when (historyEvents.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> item { LoadingItem() }
            is LoadState.Error -> item { ErrorItem { historyEvents.retry() } }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHistoryEventContent() {
    val historyEvents = mutableListOf<HistoryEvent>()
    repeat(10) {
        historyEvents.add(
            HistoryEvent(
                "",
                "http://www.spacex.com/news/2013/02/11/flight-4-launch-update-0",
                "Falcon reaches Earth orbit",
                1222643700,
                "Falcon 1 becomes the first privately developed liquid-fuel rocket to reach Earth orbit.",
            )
        )
    }
    val lazyPagingHistoryEvents = flowOf(PagingData.from(historyEvents)).collectAsLazyPagingItems()

    HistoryEventContent(lazyPagingHistoryEvents)
}