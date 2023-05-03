package com.app.feature.crew

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
import com.app.core.model.CrewMember
import com.app.core.model.sort.CrewMemberSortType
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
fun CrewTab(viewModel: CrewMembersViewModel = hiltViewModel()) {
    val crewMembers = viewModel.crewMembers.collectAsLazyPagingItems()
    val sortType = viewModel.sortType.collectAsState()
    val uiEffects = viewModel.uiEffects.collectAsState(initial = null)
    val onSortTypeClicked: (CrewMembersAction.ChangeSortType) -> Unit = { action ->
        viewModel.submitAction(CrewMembersAction.ChangeSortType(action.type))
    }
    val onQueryChanged: (CrewMembersAction.ChangeQuery) -> Unit = { action ->
        viewModel.submitAction(CrewMembersAction.ChangeQuery(action.query))
    }
    handleUiEffects(uiEffects, crewMembers)
    CrewMembersContent(
        crewMembers = crewMembers,
        sortType = sortType,
        onSortTypeClicked = onSortTypeClicked,
        onQueryChanged = onQueryChanged
    )

}

@Composable
private fun CrewMembersContent(
    modifier: Modifier = Modifier,
    crewMembers: LazyPagingItems<CrewMember>,
    sortType: State<CrewMemberSortType>,
    onSortTypeClicked: (CrewMembersAction.ChangeSortType) -> Unit,
    onQueryChanged: (CrewMembersAction.ChangeQuery) -> Unit,
) {
    Column(modifier = modifier) {
        SearchField(onQueryChanged = onQueryChanged)
        when (val refreshLoadState = crewMembers.loadState.refresh) {
            is LoadState.Loading -> LoadingColumn()
            is LoadState.Error -> {
                if (crewMembers.itemCount > 0) {
                    CrewMembersSortTypeWithList(crewMembers, sortType, onSortTypeClicked)
                    return
                }
                val isInternetError = refreshLoadState.error is UnknownHostException
                if (isInternetError)
                    ErrorColumn(
                        textRes = R.string.spacex_app_error_internet,
                        onClick = { crewMembers.refresh() }
                    )
                else
                    ErrorColumn(onClick = { crewMembers.refresh() })
            }
            else -> CrewMembersSortTypeWithList(crewMembers, sortType, onSortTypeClicked)
        }
    }
}

@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    onQueryChanged: (CrewMembersAction.ChangeQuery) -> Unit,
) {
    val searchQuery = remember { mutableStateOf("") }
    SpaceXSearchField(
        modifier = modifier.fillMaxWidth(),
        titleRes = R.string.spacex_app_search_crew_members,
        text = searchQuery,
        onValueChanged = {
            onQueryChanged.invoke(CrewMembersAction.ChangeQuery(it))
        },
    )
}

@Composable
private fun CrewMembersSortTypeWithList(
    crewMembers: LazyPagingItems<CrewMember>,
    sortType: State<CrewMemberSortType>,
    onSortTypeClicked: (CrewMembersAction.ChangeSortType) -> Unit,
) {
    Column {
        DropDownMenu(sortType, onSortTypeClicked)
        LazyCrewMembersColumn(crewMembers)
    }
}

@Composable
private fun DropDownMenu(sortType: State<CrewMemberSortType>, onSortTypeClicked: (CrewMembersAction.ChangeSortType) -> Unit) {
    val selectedSortType = stringResource(getSelectedSortTypeResId(sortType.value))
    DropDownMenuWithTitle(selectedSortType = selectedSortType) {
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_asc,
            onClick = {
                onSortClick(CrewMemberSortType.NAME_ASC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == CrewMemberSortType.NAME_ASC.value
            }
        )
        SpaceXDropdownMenuItemWithCheckedIcon(
            titleRes = R.string.spacex_app_sort_type_name_desc,
            onClick = {
                onSortClick(CrewMemberSortType.NAME_DESC, onSortTypeClicked)
            },
            showCheckedIcon = {
                sortType.value.value == CrewMemberSortType.NAME_DESC.value
            }
        )
    }
}

@Composable
private fun LazyCrewMembersColumn(crewMembers: LazyPagingItems<CrewMember>) {
    LazyColumn(content = {
        items(crewMembers) { crewMember ->
            crewMember?.let { CrewMemberCard(it) }
        }
        when (crewMembers.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> item { LoadingItem() }
            is LoadState.Error -> item { ErrorItem { crewMembers.retry() } }
            else -> throw IllegalStateException("Not all LoadState handled")
        }
    })
}

@Preview
@Composable
private fun PreviewCrewMembersTab() {
    val crewMembers = mutableListOf<CrewMember>()
    repeat(10) {
        crewMembers.add(
            CrewMember(
                "123",
                "Robert Behnken",
                "NASA",
                "https://imgur.com/0smMgMH.png",
                "https://en.wikipedia.org/wiki/Robert_L._Behnken",
                CrewMemberStatus.ACTIVE.name,
            )
        )
    }
    val lazyPagingCrewMembers = flowOf(PagingData.from(crewMembers)).collectAsLazyPagingItems()
    val sortType = remember { mutableStateOf(CrewMemberSortType.NAME_ASC) }
    CrewMembersContent(
        crewMembers = lazyPagingCrewMembers,
        sortType = sortType,
        onSortTypeClicked = {},
        onQueryChanged = {}
    )
}

private fun handleUiEffects(uiEffects: State<CrewMembersUiEffect?>, crewMembers: LazyPagingItems<CrewMember>) {
    if (uiEffects.value == null) return
    when (uiEffects.value) {
        is CrewMembersUiEffect.ChangeSortType, is CrewMembersUiEffect.QueryChanged -> crewMembers.refresh()
        else -> {}
    }
}

private fun onSortClick(type: CrewMemberSortType, onSortTypeClicked: (CrewMembersAction.ChangeSortType) -> Unit) {
    onSortTypeClicked.invoke(CrewMembersAction.ChangeSortType(type))
}

private fun getSelectedSortTypeResId(sortType: CrewMemberSortType): Int {
    return when (sortType) {
        CrewMemberSortType.NAME_ASC -> R.string.spacex_app_sort_type_name_asc
        CrewMemberSortType.NAME_DESC -> R.string.spacex_app_sort_type_name_desc
    }
}