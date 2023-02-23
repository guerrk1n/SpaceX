package com.app.feature.rockets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.core.model.Rocket
import com.app.core.model.SortType
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
                RocketsSoringWithList(rockets, sortType, openRocketDetail, onSortTypeClicked)
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
        else -> RocketsSoringWithList(rockets, sortType, openRocketDetail, onSortTypeClicked)
    }
}

@Composable
private fun RocketsSoringWithList(
    rockets: LazyPagingItems<Rocket>,
    sortType: State<SortType>,
    openRocketDetail: (String) -> Unit,
    onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit,
) {
    Column {
        DropDownMenuWithTitle(sortType, onSortTypeClicked)
        LazyRocketsColumn(rockets, openRocketDetail)
    }
}

@Composable
private fun DropDownMenuWithTitle(sortType: State<SortType>, onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.spacex_app_rocket_sort_type),
            style = MaterialTheme.typography.overline,
        )
        DropDownList(sortType, onSortTypeClicked)
    }
}

@Composable
private fun DropDownList(sortType: State<SortType>, onSortTypeClicked: (RocketsAction.ChangeSortType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.spacex_app_rocket_sort_type))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SpaceXDropdownMenuItemWithCheckedIcon(
                textRes = R.string.spacex_app_rocket_sort_type_asc,
                onClick = {
                    onSortClick(SortType.ASC, onSortTypeClicked)
                    expanded = false
                },
                showCheckedIcon = {
                    sortType.value.value == SortType.ASC.value
                }
            )

            SpaceXDropdownMenuItemWithCheckedIcon(
                textRes = R.string.spacex_app_rocket_sort_type_desc,
                onClick = {
                    onSortClick(SortType.DESC, onSortTypeClicked)
                    expanded = false
                },
                showCheckedIcon = {
                    sortType.value.value == SortType.DESC.value
                }
            )
        }
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
//    RocketsContent(lazyPagingRockets) { }
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
