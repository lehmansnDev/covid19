package de.simon.covid19.android.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import de.simon.covid19.android.R
import de.simon.covid19.android.ui.navigation.NavGraph
import de.simon.covid19.android.ui.themes.Covid19Theme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Covid19)
        super.onCreate(savedInstanceState)

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Covid19Theme {
//                ProvideWindowInsets {
//                }
                App()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun App() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            // Make this background because of white flicker at startup
            .background(
                brush = Brush.linearGradient(
                    listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
                    Offset.Zero,
                    Offset.Infinite,
                )
            ), color = Color.Transparent
    ) {
        NavGraph()
    }
}