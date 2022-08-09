package com.app.feature.rockets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.core.designsystem.theme.colorGreen
import com.app.core.model.Rocket
import com.app.core.ui.card.SpaceXCardHeader
import com.app.core.ui.card.SpaceXCardPhoto
import com.app.core.ui.card.SpaceXCardStatus
import com.app.core.ui.card.SpaceXCardTitle


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RocketCard(
    rocket: Rocket,
    openRocketDetail: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 25.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(5),
        onClick = { openRocketDetail(rocket.id) },
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpaceXCardPhoto(
                modifier = Modifier
                    .height(85.dp),
                rocket.images.first(),
                rocket.name,
                R.drawable.ic_rocket_photo_placeholder
            )
            RocketInfo(rocket)
        }
    }
}

@Composable
private fun RocketInfo(rocket: Rocket) {
    Column(modifier = Modifier.padding(start = 50.dp)) {
        SpaceXCardHeader(header = stringResource(R.string.spacex_app_rocket_overline))
        SpaceXCardTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            title = rocket.name,
        )
        RocketStatus(rocket)
    }
}

@Composable
private fun RocketStatus(rocket: Rocket) {
    val stringRes: Int
    val backgroundColor = if (rocket.active) {
        stringRes = R.string.spacex_app_active
        colorGreen
    } else {
        stringRes = R.string.spacex_app_inactive
        Color.Red
    }
    SpaceXCardStatus(
        modifier = Modifier.padding(top = 20.dp),
        status = stringResource(stringRes),
        backgroundColor = backgroundColor
    )
}

@Preview
@Composable
private fun PreviewRocketCard() {
    val rocket = Rocket(
        "5e9d0d95eda69955f709d1eb",
        "Falcon 1",
        false,
        listOf("https://imgur.com/DaCfMsj.jpg"),
    )
    RocketCard(rocket) {}
}