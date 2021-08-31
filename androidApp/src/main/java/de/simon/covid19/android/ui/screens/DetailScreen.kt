package de.simon.covid19.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.simon.covid19.android.R
import de.simon.covid19.android.ui.views.FlagView
import de.simon.covid19.android.ui.views.GlobalStatisticView
import de.simon.covid19.android.viewModels.DetailViewModel
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import java.util.*

@Composable
fun DetailScreen(countryCode: String, backPress: () -> Unit) {
    val viewModel = getViewModel<DetailViewModel>(
        parameters = { parametersOf(countryCode) }
    )

    val viewState = viewModel.viewState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
                    Offset.Zero,
                    Offset.Infinite,
                )
            ), color = Color.Transparent
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Column {
                Spacer(modifier = Modifier.statusBarsHeight())
                Row(modifier = Modifier.padding(8.dp, 0.dp)) {
                    IconButton(modifier = Modifier.padding(4.dp), onClick = backPress) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp),
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Column(
                modifier = Modifier.padding(16.dp, 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.statusBarsHeight())
                CountryChip(
                    country = viewState.value.countrySummary.country,
                    flagUrl = viewState.value.countrySummary.flagUrl
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlobalStatisticView(
                        Modifier.weight(1f),
                        viewState.value.countrySummary.totalDeaths,
                        viewState.value.countrySummary.newDeaths,
                        MaterialTheme.typography.subtitle1,
                        R.drawable.skull,
                        28.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    GlobalStatisticView(
                        Modifier.weight(1.5f),
                        viewState.value.countrySummary.totalConfirmed,
                        viewState.value.countrySummary.newConfirmed,
                        MaterialTheme.typography.h5,
                        R.drawable.virus,
                        32.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    GlobalStatisticView(
                        Modifier.weight(1f),
                        viewState.value.countrySummary.totalRecovered,
                        viewState.value.countrySummary.newRecovered,
                        MaterialTheme.typography.subtitle1,
                        R.drawable.virus_shield,
                        28.dp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CountryChip(country: String, flagUrl: String) {
    Card(
        modifier = Modifier
            .padding(40.dp, 2.dp, 40.dp, 2.dp)
            .wrapContentWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(50),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (country.length > 25) {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 8.dp, 8.dp)
                        .weight(1f),
                    text = country,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 8.dp, 8.dp),
                    text = country,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            FlagView(flagUrl = flagUrl, size = 32.dp)
        }
    }
}
