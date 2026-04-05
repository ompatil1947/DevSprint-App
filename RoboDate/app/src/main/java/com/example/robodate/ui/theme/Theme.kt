package com.example.robodate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkSpaceBackground,
    surface = CardSurfaceBackground,
    surfaceVariant = CardSurfaceBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    primary = AccentColor,
    onPrimary = DarkSpaceBackground,
    secondary = AccentSecondary,
    onSecondary = DarkSpaceBackground,
    error = MatchNo,
    onError = TextPrimary,
    outline = CardBorderColor,
    inverseSurface = TextPrimary,
    inverseOnSurface = DarkSpaceBackground
)

@Composable
fun RoboDateTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
