package com.app.feature.launchpads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.core.designsystem.theme.colorGreen
import com.app.core.model.Launchpad
import com.app.core.ui.card.SpaceXCardHeader
import com.app.core.ui.card.SpaceXCardPhoto
import com.app.core.ui.card.SpaceXCardStatus
import com.app.core.ui.card.SpaceXCardSubTitle
import com.app.core.ui.card.SpaceXCardTitle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchpadCard(
    launchpad: Launchpad,
    openLaunchpadDetail: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 25.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(5),
        onClick = { openLaunchpadDetail(launchpad.id) },
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LaunchpadPhotoWithStatus(launchpad)
            LaunchpadInfo(launchpad)
        }
    }
}

@Composable
private fun LaunchpadPhotoWithStatus(launchpad: Launchpad) {
    Column(
        modifier = Modifier.width(85.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SpaceXCardPhoto(
            modifier = Modifier.height(85.dp),
            model = launchpad.image,
            contentDescriptionName = launchpad.fullName,
            placeholderRes = R.drawable.ic_launchpad_photo_placeholder
        )
        LaunchpadStatus(launchpad)
    }
}

@Composable
private fun LaunchpadInfo(launchpad: Launchpad) {
    Column(modifier = Modifier.padding(start = 50.dp)) {
        SpaceXCardHeader(header = stringResource(R.string.spacex_app_launchpad_overline))
        SpaceXCardTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            title = launchpad.name,
        )
        SpaceXCardSubTitle(
            modifier = Modifier
                .fillMaxWidth(),
            subTitle = launchpad.fullName,
        )
    }
}

@Composable
private fun LaunchpadStatus(launchpad: Launchpad) {
    val backgroundColor = when (launchpad.status) {
        LaunchpadStatus.ACTIVE.value -> colorGreen
        LaunchpadStatus.INACTIVE.value -> Color.Red
        LaunchpadStatus.RETIRED.value -> Color.Red
        else -> Color.Gray
    }
    SpaceXCardStatus(
        modifier = Modifier.padding(top = 20.dp),
        status = launchpad.status,
        backgroundColor = backgroundColor
    )
}

@Preview
@Composable
private fun Preview() {
    LaunchpadCard(
        Launchpad(
            "5e9e4501f5090910d4566f83",
            "VAFB SLC 3W",
            "Vandenberg Space Force Base Space Launch Complex 3W",
            "retired",
            "https://i.imgur.com/7uXe1Kv.png",
        )
    ) {}
}