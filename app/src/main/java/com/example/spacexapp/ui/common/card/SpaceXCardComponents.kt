package com.example.spacexapp.ui.common.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spacexapp.R
import com.example.spacexapp.ui.theme.googleSansFamily

@Composable
fun SpaceXCardHeader(modifier: Modifier = Modifier, header: String) {
    Text(
        modifier = modifier,
        text = header,
        style = MaterialTheme.typography.overline,
    )
}

@Composable
fun SpaceXCardTitle(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.h3,
    )
}

@Composable
fun SpaceXCardDetails(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp),
    details: String,
) {
    Text(
        modifier = modifier,
        text = details,
        style = MaterialTheme.typography.body2,
    )
}

@Composable
fun SpaceXCardStatus(
    modifier: Modifier = Modifier,
    status: String,
    backgroundColor: Color,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        backgroundColor = backgroundColor,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            text = status,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = googleSansFamily,
        )
    }
}

@Composable
fun SpaceXCardPhoto(
    modifier: Modifier = Modifier,
    model: Any,
    contentDescriptionName: String,
    @DrawableRes placeholderRes: Int,
) {
    val contentDescription = stringResource(
        R.string.spacex_app_content_description_photo,
        contentDescriptionName
    )
    AsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        placeholder = painterResource(placeholderRes),
        error = painterResource(placeholderRes),
    )
}