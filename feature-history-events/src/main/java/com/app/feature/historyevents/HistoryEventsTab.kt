package com.app.feature.historyevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.lazylists.ErrorItem
import com.app.core.ui.lazylists.LoadingItem
import com.app.core.ui.loading.LoadingColumn
import com.app.core.model.HistoryEvent
import com.app.core.model.SortType
import com.app.core.ui.dropdown.DropDownMenuWithTitle
import com.app.core.ui.dropdown.SpaceXDropdownMenuItemWithCheckedIcon
import com.app.feature.history.events.R
import kotlinx.coroutines.flow.flowOf
import java.net.UnknownHostException

@Composable
fun HistoryEventsTab(viewModel: HistoryEventsViewModel = hiltViewModel()) {
    val historyEvents = viewModel.historyEvents.collectAsLazyPagingItems()
    val sortType = viewModel.sortType.collectAsState()
    val uiEffects = viewModel.uiEffects.collectAsState(initial = null)
    val onSortTypeClicked: (HistoryEventsAction.ChangeSortType) -> Unit = { action ->
        viewModel.submitAction(HistoryEventsAction.ChangeSortType(action.type))
    }

    handleUiEffects(uiEffects, historyEvents)
    HistoryEventContent(historyEvents, sortType, onSortTypeClicked)
}

@Composable
private fun HistoryEventContent(
    historyEvents: LazyPagingItems<HistoryEvent>,
    sortType: State<SortType>,
    onSortTypeClicked: (HistoryEventsAction.ChangeSortType) -> Unit,
) {
    when (val refreshLoadState = historyEvents.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (historyEvents.itemCount > 0) {
                HistoryEventsSortTypeWithList(historyEvents, sortType, onSortTypeClicked)
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
        else -> HistoryEventsSortTypeWithList(historyEvents, sortType, onSortTypeClicked)
    }
}

@Composable
private fun HistoryEventsSortTypeWithList(
    historyEvents: LazyPagingItems<HistoryEvent>,
    sortType: State<SortType>,
    onSortTypeClicked: (HistoryEventsAction.ChangeSortType) -> Unit,
) {
    Column {
        DropDownMenu(sortType, onSortTypeClicked)
        LazyHistoryEventsColumn(historyEvents)
    }
}

@Composable
private fun DropDownMenu(
    sortType: State<SortType>,
    onSortTypeClicked: (HistoryEventsAction.ChangeSortType) -> Unit,
) {
    val selectedSortType = stringResource(getSelectedSortTypeResId(sortType.value))
    DropDownMenuWithTitle(selectedSortType = selectedSortType) {
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_title_asc,
            onClick = {
                onSortClick(SortType.NAME_ASC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == SortType.NAME_ASC.value
            }
        )

        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_title_desc,
            onClick = {
                onSortClick(SortType.NAME_DESC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == SortType.NAME_DESC.value
            }
        )
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
            else -> throw IllegalStateException("Not all LoadState handled")
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
    val sortType = remember { mutableStateOf(SortType.NAME_ASC) }
    HistoryEventContent(lazyPagingHistoryEvents, sortType) {}
}

private fun handleUiEffects(uiEffects: State<HistoryEventsUiEffect?>, rockets: LazyPagingItems<HistoryEvent>) {
    if (uiEffects.value == null) return
    when (uiEffects.value) {
        is HistoryEventsUiEffect.ChangeSortType -> {
            rockets.refresh()
        }
        else -> {}
    }
}

private fun onSortClick(type: SortType, onSortTypeClicked: (HistoryEventsAction.ChangeSortType) -> Unit) {
    onSortTypeClicked.invoke(HistoryEventsAction.ChangeSortType(type))
}

private fun getSelectedSortTypeResId(sortType: SortType): Int {
    return when (sortType) {
        SortType.NAME_ASC -> R.string.spacex_app_sort_type_title_asc
        SortType.NAME_DESC -> R.string.spacex_app_sort_type_title_desc
    }
}