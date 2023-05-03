package com.app.feature.rockets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.core.model.Rocket
import com.app.core.model.sort.RocketSortType
import com.app.core.ui.dropdown.DropDownMenuWithTitle
import com.app.core.ui.dropdown.SpaceXDropdownMenuItemWithCheckedIcon
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.lazylists.ErrorItem
import com.app.core.ui.lazylists.LoadingItem
import com.app.core.ui.loading.LoadingColumn
import com.app.core.ui.text.SpaceXSearchField
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
    val onQueryChanged: (RocketsAction.ChangeQuery) -> Unit = { action ->
        viewModel.submitAction(RocketsAction.ChangeQuery(action.query))
    }
    handleUiEffects(uiEffects, rockets)
    RocketsContent(
        rockets = rockets,
        sortType = sortType,
        openRocketDetail = openRocketDetail,
        onSortTypeClicked = onSortTypeClicked,
        onQueryChanged = onQueryChanged,
    )
}

@Composable
private fun RocketsContent(
    modifier: Modifier = Modifier,
    rockets: LazyPagingItems<Rocket>,
    sortType: State<RocketSortType>,
    openRocketDetail: (String) -> Unit,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
    onQueryChanged: (RocketsAction.ChangeQuery) -> Unit,
) {
    Column(modifier = modifier) {
        SearchField(onQueryChanged = onQueryChanged)
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
}

@Composable
private fun RocketsSortTypeWithList(
    rockets: LazyPagingItems<Rocket>,
    sortType: State<RocketSortType>,
    openRocketDetail: (String) -> Unit,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
) {
    DropDownMenu(
        sortType = sortType,
        onSortTypeClicked = onSortTypeClicked,
    )
    LazyRocketsColumn(
        rockets = rockets,
        openRocketDetail = openRocketDetail,
    )
}

@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    onQueryChanged: (RocketsAction.ChangeQuery) -> Unit,
) {
    val searchQuery = remember { mutableStateOf("") }
    SpaceXSearchField(
        modifier = modifier.fillMaxWidth(),
        titleRes = R.string.spacex_app_search_rockets,
        text = searchQuery,
        onValueChanged = {
            onQueryChanged.invoke(RocketsAction.ChangeQuery(it))
        },
    )
}

@Composable
private fun DropDownMenu(
    modifier: Modifier = Modifier,
    sortType: State<RocketSortType>,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
) {
    val selectedSortType = stringResource(getSelectedSortTypeResId(sortType.value))
    DropDownMenuWithTitle(
        modifier = modifier,
        selectedSortType = selectedSortType,
    ) {
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_asc,
            onClick = {
                onSortClick(RocketSortType.NAME_ASC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == RocketSortType.NAME_ASC.value
            }
        )

        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_desc,
            onClick = {
                onSortClick(RocketSortType.NAME_DESC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == RocketSortType.NAME_DESC.value
            }
        )
    }
}


@Composable
private fun LazyRocketsColumn(
    modifier: Modifier = Modifier,
    rockets: LazyPagingItems<Rocket>,
    openRocketDetail: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        content = {
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
    val sortType = remember { mutableStateOf(RocketSortType.NAME_ASC) }

    RocketsContent(
        rockets = lazyPagingRockets,
        sortType = sortType,
        openRocketDetail = {},
        onSortTypeClicked = {},
        onQueryChanged = {}
    )
}

private fun handleUiEffects(uiEffects: State<RocketsUiEffect?>, rockets: LazyPagingItems<Rocket>) {
    if (uiEffects.value == null) return
    when (uiEffects.value) {
        is RocketsUiEffect.ChangedSortType, is RocketsUiEffect.QueryChanged -> rockets.refresh()
        else -> {}
    }
}

private fun onSortClick(type: RocketSortType, onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit) {
    onSortTypeClicked.invoke(RocketsAction.ChangeSortType(type))
}

private fun getSelectedSortTypeResId(sortType: RocketSortType): Int {
    return when (sortType) {
        RocketSortType.NAME_ASC -> R.string.spacex_app_sort_type_name_asc
        RocketSortType.NAME_DESC -> R.string.spacex_app_sort_type_name_desc
    }
}
