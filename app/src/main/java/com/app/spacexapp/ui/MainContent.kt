package com.app.spacexapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.feature.crew.CrewTab
import com.app.feature.historyevents.HistoryEventsTab
import com.app.feature.rockets.RocketsTab
import com.app.core.designsystem.theme.colorRed
import com.app.core.designsystem.theme.googleSansFamily
import com.app.feature.launchpads.LaunchpadTab
import com.app.spacexapp.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    openRocketDetail: (String) -> Unit,
    openLaunchpadDetail: (String) -> Unit,
) {
    val systemUiComposable = rememberSystemUiController()
    SideEffect { systemUiComposable.setSystemBarsColor(color = Color.Black) }

    Scaffold(
        topBar = { Toolbar() },
        backgroundColor = Color.Black,
        content = {
            MainContentBox(
                openRocketDetail,
                openLaunchpadDetail,
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContentBox(
    openRocketDetail: (String) -> Unit,
    openLaunchpadDetail: (String) -> Unit,
) {
    Box(modifier = Modifier.padding(top = 70.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ),
        ) {
            val pagerState = rememberPagerState()
            val tabs = stringArrayResource(R.array.title_tab_main)
            MainTabRow(pagerState, tabs)
            MainHorizontalPager(pagerState, tabs.size, openRocketDetail, openLaunchpadDetail)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainTabRow(pagerState: PagerState, pages: Array<String>) {
    val coroutineScope = rememberCoroutineScope()
    ScrollableTabRow(
        modifier = Modifier.padding(vertical = 40.dp),
        edgePadding = 20.dp,
        backgroundColor = Color.Transparent,
        selectedTabIndex = pagerState.currentPage,
        divider = { },
        indicator = {},
    ) {
        pages.forEachIndexed { index: Int, title: String ->
            val isSelected = pagerState.currentPage == index
            val modifier = when (index) {
                0 -> Modifier.padding(end = 15.dp)
                pages.size.minus(1) -> Modifier.padding(start = 15.dp)
                else -> Modifier.padding(horizontal = 15.dp)
            }
            Tab(
                modifier = modifier,
                content = {
                    Text(
                        text = title,
                        fontFamily = googleSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSelected) colorRed else Color.Black
                    )
                },
                selected = isSelected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainHorizontalPager(
    pagerState: PagerState,
    size: Int,
    openRocketDetail: (String) -> Unit,
    openLaunchpadDetail: (String) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        count = size,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        itemSpacing = 1.dp
    ) { pageIndex ->
        when (TABS.values()[pageIndex]) {
            TABS.ROCKETS -> RocketsTab(openRocketDetail)
            TABS.CREW -> CrewTab()
            TABS.HISTORY_EVENTS -> HistoryEventsTab()
            TABS.LAUNCHPADS -> LaunchpadTab(openLaunchpadDetail)
        }
    }
}

@Composable
fun Toolbar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = googleSansFamily,
                fontWeight = FontWeight.Medium,
            )
        },
        backgroundColor = Color.Black,
    )
}

@Preview
@Composable
fun PreviewMainContent() {
    MainScreen({}, {})
}
