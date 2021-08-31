package de.simon.covid19.android.ui.views

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.util.CoilUtils
import de.simon.covid19.android.R
import de.simon.covid19.android.ui.themes.Crimson
import de.simon.covid19.android.ui.themes.DarkGray
import okhttp3.OkHttpClient
import java.text.NumberFormat
import java.util.*

@Composable
fun CountryListView(country: CountrySummary, selectCountry: (String) -> Unit) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.clickable(onClick = { selectCountry(country.countryCode) }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IndexView(country.index)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                CountryNameText(country.country)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CountryStatisticView(
                        Modifier.weight(1f),
                        country.totalConfirmed,
                        country.newConfirmed,
                        R.drawable.virus,
                        Crimson
                    )
                    CountryStatisticView(
                        Modifier.weight(1f),
                        country.totalDeaths,
                        country.newDeaths,
                        R.drawable.skull,
                        DarkGray
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            FlagView(flagUrl = country.flagUrl)
        }
    }
}

fun cachingImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .okHttpClient {
            OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(context))
                .build()
        }
        .build()
}

fun calcPadding(size: Dp): Dp {
    val paddingValue = size.value / 6
    return Dp(paddingValue)
}

@Composable
fun CountryNameText(country: String) {
    Text(
        text = country,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun IndexView(index: Int) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
                    Offset.Zero,
                    Offset.Infinite,
                )
            )
            .padding(16.dp)
            .border(
                border = BorderStroke(2.dp, color = MaterialTheme.colors.onPrimary),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$index",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Composable
fun CountryStatisticView(modifier: Modifier, total: Int, new: Int, iconResId: Int, color: Color) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp),
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(total),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                fontSize = 12.sp
            )
            if (new > 0) {
                Text(
                    text = "+ ${
                        NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(new)
                    }",
                    style = MaterialTheme.typography.subtitle2,
                    color = color,
                    fontSize = 10.sp
                )
            } else {
                Text(
                    text = NumberFormat.getNumberInstance(Locale.getDefault())
                        .format(new),
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
            }
        }
    }
}