package de.simon.covid19.android.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import de.simon.covid19.Greeting
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import de.simon.covid19.android.R
import de.simon.covid19.android.themes.Covid19Theme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

fun greet(): String {
    return Greeting().greeting()
}

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
        //NavGraph()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = greet(),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}