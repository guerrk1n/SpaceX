package com.app.feature.launchpad.detail

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.core.common.Constants
import com.app.core.designsystem.theme.colorRed
import com.app.core.designsystem.theme.googleSansFamily
import com.app.core.model.LaunchpadDetail
import com.app.core.ui.buttons.BackButton
import com.app.core.ui.error.ErrorColumn
import com.app.core.ui.loading.LoadingColumn
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@Composable
fun LaunchpadDetailScreen(
    viewModel: LaunchpadDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState = viewModel.state.collectAsState().value
    LaunchpadDetailContent(
        navigateUp = navigateUp,
        refreshContent = { viewModel.getLaunchpadDetails() },
        uiState = uiState,
    )
}

@Composable
private fun LaunchpadDetailContent(
    navigateUp: () -> Unit,
    refreshContent: () -> Unit,
    uiState: LaunchpadDetailViewModel.LaunchpadDetailViewState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)
            .padding(horizontal = 35.dp)
            .padding(bottom = 40.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        TopBar(navigateUp, uiState.launchpadDetail)
        when {
            uiState.loading -> LoadingColumn(color = colorRed)

            uiState.error != null -> ErrorColumn(color = colorRed, onClick = refreshContent)

            else -> LaunchpadInfo(uiState)
        }
    }
}

@Composable
private fun TopBar(navigateUp: () -> Unit, launchpadDetail: LaunchpadDetail?) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(vertical = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(navigateUp)
        launchpadDetail?.let {
            IconButton(onClick = { shareClick(context, it.name, it.details) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(R.string.spacex_app_send),
                    tint = Color.Unspecified,
                )
            }
        }

    }
}

@Composable
private fun LaunchpadInfo(uiState: LaunchpadDetailViewModel.LaunchpadDetailViewState) {
    uiState.launchpadDetail?.let {
        InfoBlock(R.string.spacex_app_feature_launchpad_title, it.name)
        InfoBlock(R.string.spacex_app_feature_launchpad_full_name, it.fullName)
        InfoBlock(R.string.spacex_app_feature_launchpad_status, it.status)
        InfoBlock(R.string.spacex_app_feature_launchpad_locality, it.locality)
        InfoBlock(R.string.spacex_app_feature_launchpad_region, it.region)
        InfoBlock(
            R.string.spacex_app_feature_launchpad_landing_attempts_and_successes,
            stringResource(
                R.string.spacex_app_feature_launchpad_landing_successes_and_attempts_format,
                it.landingSuccesses,
                it.landingAttempts,
            )
        )
        InfoBlock(R.string.spacex_app_feature_launchpad_time_zone, it.timeZone)
        InfoBlock(R.string.spacex_app_feature_launchpad_details, it.details)
        PhotoInfo(it.images, it.name)
    }
}

@Composable
private fun InfoBlock(@StringRes titleRes: Int, info: String) {
    val title = stringResource(titleRes)
    Text(text = title, style = MaterialTheme.typography.overline, color = colorRed)
    Text(
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
        text = info,
        fontSize = 18.sp,
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.White
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PhotoInfo(images: List<String>, contentDescription: String) {
    Text(
        text = stringResource(R.string.spacex_app_feature_launchpad_sneak_peak),
        style = MaterialTheme.typography.overline,
        color = colorRed
    )
    HorizontalPager(
        modifier = Modifier.padding(top = 25.dp),
        state = rememberPagerState(),
        count = images.size,
        itemSpacing = 1.dp,
    ) { pageIndex ->
        val animation = getPhotoSwipeAnimation(this@HorizontalPager, pageIndex)

        Card(
            modifier = Modifier
                .graphicsLayer(animation)
                .height(300.dp),
        ) {
            AsyncImage(
                model = images[pageIndex],
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.spacex_app_content_description_photo_launchpad_detail, contentDescription),
            )
        }
    }
}

private fun shareClick(context: Context, title: String, description: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_TEXT, description)
        type = Constants.MimeTypes.TEXT_PLAIN
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}

@OptIn(ExperimentalPagerApi::class)
private fun getPhotoSwipeAnimation(
    scope: PagerScope,
    pageIndex: Int,
): GraphicsLayerScope.() -> Unit = {
    val pageOffset = scope.calculateCurrentOffsetForPage(pageIndex).absoluteValue
    lerp(
        start = 0.85f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    ).also { scale ->
        scaleX = scale
        scaleY = scale
    }
    alpha = lerp(
        start = 0.5f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
}

@Preview
@Composable
private fun PreviewLaunchpadDetailContent() {
    val launchpad = LaunchpadDetail(
        "5e9e4501f5090910d4566f83",
        "VAFB SLC 3W",
        "Vandenberg Space Force Base Space Launch Complex 3W",
        "retired",
        "Vandenberg Space Force Base",
        "California",
        0,
        0,
        listOf("https://i.imgur.com/7uXe1Kv.png"),
        """
                SpaceX's original west coast launch pad for Falcon 1. It was used in a static fire test but was never employed for a launch,
                 and was abandoned due to range scheduling conflicts arising from overflying other active pads.
            """.trimIndent(),
        listOf(""),
        listOf(""),
        "America/Los_Angeles"
    )
    val launchpadDetailState = LaunchpadDetailViewModel.LaunchpadDetailViewState(launchpad)
    LaunchpadDetailContent(navigateUp = {}, refreshContent = {}, uiState = launchpadDetailState)

}