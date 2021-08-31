package de.simon.covid19.android.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.simon.covid19.android.ui.themes.TealSlateGray
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun FlagView(modifier: Modifier = Modifier, flagUrl: String, size: Dp = 48.dp) {
    Box(
        modifier = modifier
            .padding(calcPadding(size))
            .requiredSize(size)
            .size(size)
            .clip(CircleShape)
            .background(if(isSystemInDarkTheme()) TealSlateGray else Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            data = flagUrl,
            contentDescription = null,
            imageLoader = cachingImageLoader(LocalContext.current),
            modifier = Modifier
                .padding(calcPadding(size))
        )
    }
}