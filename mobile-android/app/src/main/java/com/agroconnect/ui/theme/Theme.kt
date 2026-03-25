package com.agroconnect.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Green800,
    onPrimary = White,
    primaryContainer = Green100,
    onPrimaryContainer = Green900,
    secondary = Lime600,
    onSecondary = White,
    secondaryContainer = Green50,
    onSecondaryContainer = Lime700,
    tertiary = Brown600,
    onTertiary = White,
    tertiaryContainer = Brown50,
    onTertiaryContainer = Brown800,
    error = Danger,
    onError = White,
    errorContainer = DangerLight,
    background = Gray50,
    onBackground = Gray900,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray600,
    outline = Gray300,
    outlineVariant = Gray200,
)

private val DarkColorScheme = darkColorScheme(
    primary = Green500,
    onPrimary = Green900,
    primaryContainer = Green800,
    onPrimaryContainer = Green100,
    secondary = Lime500,
    onSecondary = Lime700,
    secondaryContainer = Lime700,
    onSecondaryContainer = Green50,
    tertiary = Brown50,
    onTertiary = Brown800,
    tertiaryContainer = Brown800,
    onTertiaryContainer = Brown50,
    error = DangerLight,
    onError = Gray900,
    background = Gray900,
    onBackground = Gray100,
    surface = Gray800,
    onSurface = Gray100,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray300,
    outline = Gray500,
    outlineVariant = Gray600,
)

@Composable
fun AgroConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AgroTypography,
        content = content
    )
}
