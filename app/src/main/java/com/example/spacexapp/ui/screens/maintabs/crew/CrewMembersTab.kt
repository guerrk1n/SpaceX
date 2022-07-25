package com.example.spacexapp.ui.screens.maintabs.crew

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.spacexapp.ui.common.error.ErrorColumn
import com.example.spacexapp.ui.common.lazylists.ErrorItem
import com.example.spacexapp.ui.common.lazylists.LoadingItem
import com.example.spacexapp.ui.common.loading.LoadingColumn
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMember
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberCard
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberStatus
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

@Composable
fun CrewTab() {
    val viewmodel: CrewMembersViewModel = getViewModel()
    val crewMembers = viewmodel.crew.collectAsLazyPagingItems()

    CrewMembersContent(crewMembers)

}

@Composable
private fun CrewMembersContent(crewMembers: LazyPagingItems<CrewMember>) {
    when (crewMembers.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> ErrorColumn()
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
            "Robert Behnken",
            "NASA",
            "https://imgur.com/0smMgMH.png",
            "https://en.wikipedia.org/wiki/Robert_L._Behnken",
            CrewMemberStatus.ACTIVE.name,
            "123"
        ))
    }
    val lazyPagingCrewMembers = flowOf(PagingData.from(crewMembers)).collectAsLazyPagingItems()
    CrewMembersContent(lazyPagingCrewMembers)
}