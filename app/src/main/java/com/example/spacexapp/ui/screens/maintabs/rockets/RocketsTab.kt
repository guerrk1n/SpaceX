package com.example.spacexapp.ui.screens.maintabs.rockets

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
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketCard
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.getViewModel

@Composable
fun RocketsTab() {
    val viewmodel: RocketsViewModel = getViewModel()
    val rockets = viewmodel.rockets.collectAsLazyPagingItems()

    RocketsContent(rockets)
}

@Composable
private fun RocketsContent(rockets: LazyPagingItems<Rocket>) {
    when (rockets.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> ErrorColumn()
        else -> LazyRocketsColumn(rockets)
    }
}

@Composable
private fun LazyRocketsColumn(rockets: LazyPagingItems<Rocket>) {
    LazyColumn(content = {
        items(rockets) { rocket ->
            rocket?.let { RocketCard(it) }
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
                "Falcon 1",
                false,
                listOf("https://imgur.com/DaCfMsj.jpg"),
                "5e9d0d95eda69955f709d1eb"
            ))
    }
    val lazyPagingRockets = flowOf(PagingData.from(rockets)).collectAsLazyPagingItems()
    RocketsContent(lazyPagingRockets)
}
