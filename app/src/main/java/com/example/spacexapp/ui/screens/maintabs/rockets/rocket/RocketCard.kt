package com.example.spacexapp.ui.screens.maintabs.rockets.rocket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spacexapp.R
import com.example.spacexapp.ui.theme.colorGreen
import com.example.spacexapp.ui.theme.googleSansFamily


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
            RocketPhoto(rocket)
            RocketInfo(rocket)
        }
    }
}

@Composable
private fun RocketPhoto(rocket: Rocket) {
    val contentDescription = stringResource(
        R.string.spacex_app_content_description_photo_rocket,
        rocket.name
    )
    AsyncImage(
        modifier = Modifier
            .height(85.dp),
        model = rocket.images.first(),
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.ic_rocket_photo_placeholder)
    )
}

@Composable
private fun RocketInfo(rocket: Rocket) {
    Column(modifier = Modifier.padding(start = 50.dp)) {
        RocketHeader()
        RocketTitle(rocket)
        RocketStatus(rocket)
    }
}

@Composable
private fun RocketHeader() {
    Text(
        text = stringResource(id = R.string.spacex_app_rocket_overline),
        style = MaterialTheme.typography.overline,
    )
}

@Composable
private fun RocketTitle(rocket: Rocket) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        text = rocket.name, style = MaterialTheme.typography.h3)
}

@Composable
private fun RocketStatus(rocket: Rocket) {
    val text: String
    val backgroundColor = if (rocket.active) {
        text = stringResource(id = R.string.spacex_app_active)
        colorGreen
    } else {
        text = stringResource(id = R.string.spacex_app_inactive)
        Color.Red
    }

    Card(
        modifier = Modifier.padding(top = 20.dp),
        shape = RoundedCornerShape(6.dp),
        backgroundColor = backgroundColor,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = googleSansFamily
        )
    }
}

@Preview
@Composable
private fun PreviewRocketCard() {
    val rocket = Rocket(
        "Falcon 1",
        false,
        listOf("https://imgur.com/DaCfMsj.jpg"),
        "5e9d0d95eda69955f709d1eb"
    )
    RocketCard(rocket) {}
}