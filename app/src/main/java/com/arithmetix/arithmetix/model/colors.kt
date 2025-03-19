package com.arithmetix.arithmetix.model

import androidx.compose.ui.graphics.Color

data class NS_Colors(
    val background: Color,
    val text: Color,
    val border: Color,
    val accent: Color,
)

enum class AppThemeNames {
    light, dark, blue
}