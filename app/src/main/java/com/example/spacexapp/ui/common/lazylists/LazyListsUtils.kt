package com.example.spacexapp.ui.common.lazylists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacexapp.R

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(8.dp),
            strokeWidth = 5.dp,
            color = Color.Black
        )
    }
}

@Composable
fun ErrorItem(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 3.dp,
        shape = RoundedCornerShape(5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                painter = painterResource(R.drawable.ic_error),
                contentDescription = stringResource(R.string.spacex_app_content_description_error),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                color = Color.White,
                text = stringResource(R.string.spacex_app_error),
                fontSize = 16.sp,

                )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onClick.invoke() },
                color = Color.White,
                text = stringResource(R.string.spacex_app_retry),
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}