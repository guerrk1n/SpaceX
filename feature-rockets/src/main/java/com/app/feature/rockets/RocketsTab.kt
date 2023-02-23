package com.app.feature.rockets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.core.model.Rocket
import com.app.core.model.SortType
import com.app.core.ui.dropdown.DropDownMenuWithTitle
import com.app.core.ui.dropdown.SpaceXDropdownMenuItemWithCheckedIcon
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.lazylists.ErrorItem
import com.app.core.ui.lazylists.LoadingItem
import com.app.core.ui.loading.LoadingColumn
import kotlinx.coroutines.flow.flowOf
import java.net.UnknownHostException

@Composable
fun RocketsTab(
    openRocketDetail: (String) -> Unit,
    viewModel: RocketsViewModel = hiltViewModel(),
) {
    val rockets = viewModel.rockets.collectAsLazyPagingItems()
    val sortType = viewModel.sortType.collectAsState()
    val uiEffects = viewModel.uiEffects.collectAsState(initial = null)
    val onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit = { action ->
        viewModel.submitAction(RocketsAction.ChangeSortType(action.type))
    }
    handleUiEffects(uiEffects, rockets)
    RocketsContent(rockets, sortType, openRocketDetail, onSortTypeClicked)
}

@Composable
private fun RocketsContent(
    rockets: LazyPagingItems<Rocket>,
    sortType: State<SortType>,
    openRocketDetail: (String) -> Unit,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
) {
    when (val refreshLoadState = rockets.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (rockets.itemCount > 0) {
                RocketsSortTypeWithList(rockets, sortType, openRocketDetail, onSortTypeClicked)
                return
            }
            val isInternetError = refreshLoadState.error is UnknownHostException
            if (isInternetError)
                ErrorColumn(
                    textRes = R.string.spacex_app_error_internet,
                    onClick = { rockets.refresh() }
                )
            else
                ErrorColumn(onClick = { rockets.refresh() })

        }
        else -> RocketsSortTypeWithList(rockets, sortType, openRocketDetail, onSortTypeClicked)
    }
}

@Composable
private fun RocketsSortTypeWithList(
    rockets: LazyPagingItems<Rocket>,
    sortType: State<SortType>,
    openRocketDetail: (String) -> Unit,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
) {
    Column {
        DropDownMenu(sortType, onSortTypeClicked)
        LazyRocketsColumn(rockets, openRocketDetail)
    }
}

@Composable
private fun DropDownMenu(sortType: State<SortType>, onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit) {
    DropDownMenuWithTitle {
        SpaceXDropdownMenuItemWithCheckedIcon(
            textRes = R.string.spacex_app_sort_type_asc,
            onClick = {
                onSortClick(SortType.ASC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == SortType.ASC.value
            }
        )

        SpaceXDropdownMenuItemWithCheckedIcon(
            textRes = R.string.spacex_app_sort_type_desc,
            onClick = {
                onSortClick(SortType.DESC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == SortType.DESC.value
            }
        )
    }
}


@Composable
private fun LazyRocketsColumn(
    rockets: LazyPagingItems<Rocket>,
    openRocketDetail: (String) -> Unit,
) {
    LazyColumn(content = {
        items(rockets) { rocket ->
            rocket?.let { RocketCard(it, openRocketDetail) }
        }
        when (rockets.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> item { LoadingItem() }
            is LoadState.Error -> item { ErrorItem { rockets.retry() } }
            else -> throw IllegalStateException("Not all LoadState handled")
        }
    })
}

@Preview
@Composable
private fun PreviewRocketsTab() {
    val rockets = mutableListOf<Rocket>()
    repeat(10) {
        rockets.add(
            Rocket(
                "5e9d0d95eda69955f709d1eb",
                "Falcon 1",
                false,
                listOf("https://imgur.com/DaCfMsj.jpg"),
            )
        )
    }
    val lazyPagingRockets = flowOf(PagingData.from(rockets)).collectAsLazyPagingItems()
    val sortType = remember { mutableStateOf(SortType.ASC) }

    RocketsContent(lazyPagingRockets, sortType, {}) { }
}

private fun handleUiEffects(uiEffects: State<RocketsUiEffect?>, rockets: LazyPagingItems<Rocket>) {
    if (uiEffects.value == null) return
    when (uiEffects.value) {
        is RocketsUiEffect.ChangeSortType -> {
            rockets.refresh()
        }
        else -> {}
    }
}

private fun onSortClick(type: SortType, onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit) {
    onSortTypeClicked.invoke(RocketsAction.ChangeSortType(type))
}
