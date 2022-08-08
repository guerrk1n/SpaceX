package com.app.spacexapp.ui.screens.maintabs.crew

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.spacexapp.R
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.lazylists.ErrorItem
import com.app.core.ui.lazylists.LoadingItem
import com.app.core.ui.loading.LoadingColumn
import com.app.core.model.CrewMember
import com.app.spacexapp.ui.screens.maintabs.crew.member.CrewMemberCard
import com.app.spacexapp.ui.screens.maintabs.crew.member.CrewMemberStatus
import kotlinx.coroutines.flow.flowOf
import java.net.UnknownHostException

@Composable
fun CrewTab(viewmodel: CrewMembersViewModel = hiltViewModel()) {
    val crewMembers = viewmodel.crewMembers.collectAsLazyPagingItems()

    CrewMembersContent(crewMembers)

}

@Composable
private fun CrewMembersContent(crewMembers: LazyPagingItems<CrewMember>) {
    when (val refreshLoadState = crewMembers.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (crewMembers.itemCount > 0) {
                LazyCrewMembersColumn(crewMembers)
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
        else -> LazyCrewMembersColumn(crewMembers)
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
        }
    })
}

@Preview
@Composable
private fun PreviewCrewMembersTab() {
    val crewMembers = mutableListOf<CrewMember>()
    repeat(10) {
        crewMembers.add(CrewMember(
            "123",
            "Robert Behnken",
            "NASA",
            "https://imgur.com/0smMgMH.png",
            "https://en.wikipedia.org/wiki/Robert_L._Behnken",
            CrewMemberStatus.ACTIVE.name,
        ))
    }
    val lazyPagingCrewMembers = flowOf(PagingData.from(crewMembers)).collectAsLazyPagingItems()
    CrewMembersContent(lazyPagingCrewMembers)
}