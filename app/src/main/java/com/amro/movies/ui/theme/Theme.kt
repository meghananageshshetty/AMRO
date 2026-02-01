package com.amro.movies.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorsScheme = darkColorScheme(
    primary = AmroYellow,
    secondary = AmroYellow,
    background = AmroBlue,
    surface = AmroCard,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = AmroTextPrimary,
    onSurface = AmroTextPrimary
)

private val LightColorsScheme = lightColorScheme(
    primary = AmroYellow,
    secondary = AmroYellow,
    background = AmroLightBackground,
    surface = AmroLightSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = AmroLightPrimaryText,
    onSurface = AmroLightPrimaryText
)

@Composable
fun AMROTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorsScheme
        else -> LightColorsScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}