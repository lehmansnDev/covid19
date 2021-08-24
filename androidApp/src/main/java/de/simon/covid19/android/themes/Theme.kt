package de.simon.covid19.android.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkSlateGray,
    primaryVariant = LighterBlack,
    secondary = Tan,
    background = TealDarkGray,
    surface = TealBlack,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onBackground = LightGray,
    onSurface = LightGray
)

private val LightColorPalette = lightColors(
    primary = DarkCyan,
    primaryVariant = Teal,
    secondary = Peru,
    background = LightGray,
    surface = WhiteSmoke,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun Covid19Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}