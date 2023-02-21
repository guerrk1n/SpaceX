package com.app.feature.launchpads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.core.model.Launchpad
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

    LaunchpadsContent(launchpads, openLaunchpadDetail)
}

@Composable
private fun LaunchpadsContent(
    launchpads: LazyPagingItems<Launchpad>,
    openLaunchpadDetail: (String) -> Unit,
) {
    when (val refreshLoadState = launchpads.loadState.refresh) {
        is LoadState.Loading -> LoadingColumn()
        is LoadState.Error -> {
            if (launchpads.itemCount > 0) {
                LazyLaunchpadsColumn(launchpads, openLaunchpadDetail)
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
        else -> LazyLaunchpadsColumn(launchpads, openLaunchpadDetail)
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
            else -> {}
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

    LaunchpadsContent(lazyPagingLaunchpads) {}
}