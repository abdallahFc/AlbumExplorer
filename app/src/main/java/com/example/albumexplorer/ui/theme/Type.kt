package com.example.albumexplorer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    )
)