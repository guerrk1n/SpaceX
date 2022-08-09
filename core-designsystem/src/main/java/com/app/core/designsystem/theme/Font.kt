package com.app.core.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.app.core.designsystem.R

val googleSansFamily = FontFamily(
    Font(R.font.google_sans_bold, FontWeight.Bold),
    Font(R.font.google_sans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.google_sans_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.google_sans_medium, FontWeight.Medium),
    Font(R.font.google_sans_regular),
)