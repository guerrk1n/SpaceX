package com.app.core.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Default = Typography()
val LightTypography = Typography(
    h1 = Default.h1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 32.sp
    ),
    h2 = Default.h2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 24.sp
    ),
    h3 = Default.h3.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 18.sp
    ),
    body1 = Default.body1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 18.sp
    ),
    body2 = Default.body2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 16.sp
    ),
    subtitle1 = Default.subtitle1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 14.sp
    ),
    subtitle2 = Default.subtitle2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 12.sp
    ),
    overline = Default.overline.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        fontSize = 12.sp
    ),
)

val DarkTypography = Typography(
    h1 = Default.h1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 32.sp
    ),
    h2 = Default.h2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 24.sp
    ),
    h3 = Default.h3.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 18.sp
    ),
    body1 = Default.body1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 18.sp
    ),
    body2 = Default.body2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 16.sp
    ),
    subtitle1 = Default.subtitle1.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 14.sp
    ),
    subtitle2 = Default.subtitle2.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 12.sp
    ),
    overline = Default.overline.copy(
        fontFamily = googleSansFamily,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        fontSize = 12.sp
    ),
)