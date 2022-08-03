package com.example.spacexapp.ui.screens.details.rocket

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.spacexapp.R
import com.example.spacexapp.ui.common.buttons.BackButton
import com.example.spacexapp.ui.common.error.ErrorColumn
import com.example.spacexapp.ui.common.loading.LoadingColumn
import com.example.spacexapp.ui.theme.colorRed
import com.example.spacexapp.ui.theme.googleSansFamily
import com.example.spacexapp.util.Constants
import com.google.accompanist.pager.*
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.absoluteValue

@Composable
fun RocketDetailScreen(navigateUp: () -> Unit, rocketId: String) {
    val viewModel: RocketDetailViewModel = getViewModel(parameters = { parametersOf(rocketId) })
    val uiState = viewModel.state.collectAsState().value
    RocketDetailContent(
        navigateUp = navigateUp,
        refreshContent = { viewModel.getRocketDetails() },
        uiState = uiState
    )

}

@Composable
private fun RocketDetailContent(
    navigateUp: () -> Unit,
    refreshContent: () -> Unit,
    uiState: RocketDetailViewModel.RocketDetailViewState,
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
        TopBar(navigateUp, uiState.rocketDetail)
        when {
            uiState.loading -> LoadingColumn(color = colorRed)

            uiState.error != null -> ErrorColumn(color = colorRed, onClick = refreshContent)

            else -> RocketInfo(uiState)
        }

    }
}

@Composable
private fun TopBar(navigateUp: () -> Unit, rocketDetail: RocketDetail?) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(vertical = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(navigateUp)
        rocketDetail?.let {
            IconButton(onClick = { shareClick(context, it.name, it.description) }) {
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
private fun RocketInfo(uiState: RocketDetailViewModel.RocketDetailViewState) {
    uiState.rocketDetail?.let {
        InfoBlock(R.string.spacex_app_feature_rocket_title, it.name)
        InfoBlock(R.string.spacex_app_feature_rocket_first_flight, it.firstFlight)

        val activeRes =
            if (it.active) R.string.spacex_app_active else R.string.spacex_app_inactive
        InfoBlock(R.string.spacex_app_active, stringResource(activeRes))

        InfoBlock(R.string.spacex_app_feature_rocket_details, it.description)
        WikipediaButton(it.wikipedia)
        PhotoInfo(it.images)
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

@Composable
private fun WikipediaButton(wikipedia: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = stringResource(R.string.spacex_app_link),
        style = MaterialTheme.typography.overline, color = colorRed
    )
    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 20.dp)
            .wrapContentSize()
            .clickable { uriHandler.openUri(wikipedia) },
        shape = RoundedCornerShape(6.dp),
        backgroundColor = colorRed,
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 50.dp),
            text = stringResource(R.string.spacex_app_wikipedia),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = googleSansFamily
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PhotoInfo(images: List<String>) {
    Text(
        text = stringResource(R.string.spacex_app_feature_rocket_sneak_peak),
        style = MaterialTheme.typography.overline,
        color = colorRed
    )
    HorizontalPager(
        modifier = Modifier.padding(top = 25.dp),
        state = rememberPagerState(),
        count = images.size,
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
                contentDescription = stringResource(R.string.spacex_app_content_description_photo),
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
private fun PreviewRocketDetailScreen() {
//    RocketDetailContent(uiState)
}
