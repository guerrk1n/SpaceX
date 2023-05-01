package com.app.feature.launchpads

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
import com.app.core.model.Launchpad
import com.app.core.model.sort.LaunchpadSortType
import com.app.core.ui.dropdown.DropDownMenuWithTitle
import com.app.core.ui.dropdown.SpaceXDropdownMenuItemWithCheckedIcon
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.lazylists.ErrorItem
import com.app.core.ui.lazylists.LoadingItem
import com.app.core.ui.loading.LoadingColumn
import kotlinx.coroutines.flow.flowOf
import java.net.UnknownHostException

@Composable
fun LaunchpadTab(
    openLaunchpadDetail: (String) -> Unit,
    viewModel: LaunchpadsViewModel = hiltViewModel()
) {
    val launchpads = viewModel.launchpads.collectAsLazyPagingItems()
    val sortType = viewModel.sortType.collectAsState()
    val uiEffects = viewModel.uiEffects.collectAsState(initial = null)
    val onSortTypeClicked: (LaunchpadsAction.ChangeSortType) -> Unit = { action ->
        viewModel.submitAction(LaunchpadsAction.ChangeSortType(action.type))
    }
    handleUiEffects(uiEffects, launchpads)

    LaunchpadsContent(launchpads, sortType, openLaunchpadDetail, onSortTypeClicked)
}

@Composable
private fun LaunchpadsContent(
    launchpads: LazyPagingItems<Launchpad>,
    sortType: State<LaunchpadSortType>,
    openLaunchpadDetail: (String) -> Unit,
    onSortTypeClicked: (LaunchpadsAction.ChangeSortType) -> Unit,
) {
    when (val refreshLoadState = launchpads.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (launchpads.itemCount > 0) {
                RocketsSortTypeWithList(launchpads, sortType, onSortTypeClicked, openLaunchpadDetail)
                return
            }
            val isInternetError = refreshLoadState.error is UnknownHostException
            if (isInternetError)
                ErrorColumn(
                    textRes = R.string.spacex_app_error_internet,
                    onClick = { launchpads.refresh() }
                )
            else
                ErrorColumn(onClick = { launchpads.refresh() })
        }
        else -> RocketsSortTypeWithList(launchpads, sortType, onSortTypeClicked, openLaunchpadDetail)
    }
}

@Composable
private fun RocketsSortTypeWithList(
    launchpads: LazyPagingItems<Launchpad>,
    sortType: State<LaunchpadSortType>,
    onSortTypeClicked: (LaunchpadsAction.ChangeSortType) -> Unit,
    openLaunchpadDetail: (String) -> Unit,
) {
    Column {
        DropDownMenu(sortType, onSortTypeClicked)
        LazyLaunchpadsColumn(launchpads, openLaunchpadDetail)
    }
}

@Composable
private fun DropDownMenu(sortType: State<LaunchpadSortType>, onSortTypeClicked: (LaunchpadsAction.ChangeSortType) -> Unit) {
    val selectedSortType = stringResource(getSelectedSortTypeResId(sortType.value))
    DropDownMenuWithTitle(selectedSortType = selectedSortType) {
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_asc,
            onClick = {
                onSortClick(LaunchpadSortType.NAME_ASC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == LaunchpadSortType.NAME_ASC.value
            }
        )

        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_desc,
            onClick = {
                onSortClick(LaunchpadSortType.NAME_DESC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == LaunchpadSortType.NAME_DESC.value
            }
        )
    }
}

@Composable
private fun LazyLaunchpadsColumn(
    launchpads: LazyPagingItems<Launchpad>,
    openLaunchpadDetail: (String) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.Top) {
        items(launchpads) { item ->
            item?.let { LaunchpadCard(it, openLaunchpadDetail) }
        }

        when (launchpads.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> item { LoadingItem() }
            is LoadState.Error -> item { ErrorItem { launchpads.retry() } }
            else -> throw IllegalStateException("Not all LoadState handled")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewLaunchpadsContent() {
    val launchpad = Launchpad(
        "5e9e4501f5090910d4566f83",
        "VAFB SLC 3W",
        "Vandenberg Space Force Base Space Launch Complex 3W",
        "retired",
        "https://i.imgur.com/7uXe1Kv.png",
    )
    val launchpads = mutableListOf<Launchpad>()
    repeat(10) {
        launchpads.add(launchpad)
    }
    val lazyPagingLaunchpads = flowOf(PagingData.from(launchpads)).collectAsLazyPagingItems()
    val sortType = remember { mutableStateOf(LaunchpadSortType.NAME_ASC) }

    LaunchpadsContent(lazyPagingLaunchpads, sortType, { }) {}
}

private fun handleUiEffects(uiEffects: State<LaunchpadsUiEffect?>, rockets: LazyPagingItems<Launchpad>) {
    if (uiEffects.value == null) return
    when (uiEffects.value) {
        is LaunchpadsUiEffect.ChangeSortType -> {
            rockets.refresh()
        }
        else -> {}
    }
}

private fun onSortClick(type: LaunchpadSortType, onSortTypeClicked: (LaunchpadsAction.ChangeSortType) -> Unit) {
    onSortTypeClicked.invoke(LaunchpadsAction.ChangeSortType(type))
}

private fun getSelectedSortTypeResId(sortType: LaunchpadSortType): Int {
    return when (sortType) {
        LaunchpadSortType.NAME_ASC -> R.string.spacex_app_sort_type_name_asc
        LaunchpadSortType.NAME_DESC -> R.string.spacex_app_sort_type_name_desc
    }
}