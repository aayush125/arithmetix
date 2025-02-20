package com.example.numsprint.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.numsprint.model.AppThemeNames

@Composable
fun animateColorScheme(
    targetColorScheme: ColorScheme,
    durationMillis: Int = 500
): ColorScheme {
    val background by animateColorAsState(
        targetValue = targetColorScheme.background,
        animationSpec = tween(durationMillis),
        label = "background"
    )
    val surface by animateColorAsState(
        targetValue = targetColorScheme.surface,
        animationSpec = tween(durationMillis),
        label = "surface"
    )
    val primary by animateColorAsState(
        targetValue = targetColorScheme.primary,
        animationSpec = tween(durationMillis),
        label = "primary"
    )
    val secondary by animateColorAsState(
        targetValue = targetColorScheme.secondary,
        animationSpec = tween(durationMillis),
        label = "secondary"
    )
    val tertiary by animateColorAsState(
        targetValue = targetColorScheme.tertiary,
        animationSpec = tween(durationMillis),
        label = "tertiary"
    )
    val onBackground by animateColorAsState(
        targetValue = targetColorScheme.onBackground,
        animationSpec = tween(durationMillis),
        label = "onBackground"
    )
    val onSurface by animateColorAsState(
        targetValue = targetColorScheme.onSurface,
        animationSpec = tween(durationMillis),
        label = "onSurface"
    )
    val onPrimary by animateColorAsState(
        targetValue = targetColorScheme.onPrimary,
        animationSpec = tween(durationMillis),
        label = "onPrimary"
    )
    val onSecondary by animateColorAsState(
        targetValue = targetColorScheme.onSecondary,
        animationSpec = tween(durationMillis),
        label = "onSecondary"
    )
    val onTertiary by animateColorAsState(
        targetValue = targetColorScheme.onTertiary,
        animationSpec = tween(durationMillis),
        label = "onTertiary"
    )

    return ColorScheme(
        background = background,
        surface = surface,
        primary = primary,
        secondary = secondary,
        tertiary = tertiary,
        onBackground = onBackground,
        onSurface = onSurface,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onTertiary = onTertiary,
        // Copy the rest of the colors from the target scheme
        primaryContainer = targetColorScheme.primaryContainer,
        secondaryContainer = targetColorScheme.secondaryContainer,
        tertiaryContainer = targetColorScheme.tertiaryContainer,
        onPrimaryContainer = targetColorScheme.onPrimaryContainer,
        onSecondaryContainer = targetColorScheme.onSecondaryContainer,
        onTertiaryContainer = targetColorScheme.onTertiaryContainer,
        error = targetColorScheme.error,
        onError = targetColorScheme.onError,
        errorContainer = targetColorScheme.errorContainer,
        onErrorContainer = targetColorScheme.onErrorContainer,
        outline = targetColorScheme.outline,
        outlineVariant = targetColorScheme.outlineVariant,
        scrim = targetColorScheme.scrim,
        inverseSurface = targetColorScheme.inverseSurface,
        inverseOnSurface = targetColorScheme.inverseOnSurface,
        inversePrimary = targetColorScheme.inversePrimary,
        surfaceTint = targetColorScheme.surfaceTint,
        surfaceVariant = targetColorScheme.surfaceVariant,
        onSurfaceVariant = targetColorScheme.onSurfaceVariant
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


private val BlueColorScheme = darkColorScheme(
    // #0E1D35 - Darkest blue for main background
    background = Color(0xFF0E1D35),

    // #15253F - Slightly lighter blue for surfaces
    surface = Color(0xFF15253F),

    // #2B3D5B - Medium blue for primary elements
    primary = Color(0xFF2B3D5B),

    // #7C8594 - Gray-blue for secondary elements
    secondary = Color(0xFF7C8594),

    // #C3C3C3 - Light gray for tertiary elements
    tertiary = Color(0xFFC3C3C3),

    // #DDDEE5 - Lightest gray for text/content on dark backgrounds
    onBackground = Color(0xFFDDDEE5),
    onSurface = Color(0xFFDDDEE5),
    onPrimary = Color(0xFFDDDEE5),

    // Use lighter colors for content on darker backgrounds
    onSecondary = Color.White,
    onTertiary = Color(0xFF0E1D35)  // Dark color for content on light backgrounds
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xffcab18e),
    secondary = Color(0xffE9D6B2),
    tertiary = Color(0xffac734f)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

sealed class AppTheme(val colorScheme: ColorScheme, val isDark: Boolean) {
    object light: AppTheme(LightColorScheme, false)
    object dark: AppTheme(DarkColorScheme, true)
    object blue: AppTheme(BlueColorScheme, true)
}

@Composable
fun NumSprintTheme(
    customTheme: AppTheme = AppTheme.light,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> BlueColorScheme
//        else -> LightColorScheme
//    }

    val colorScheme = animateColorScheme(customTheme.colorScheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !customTheme.isDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}