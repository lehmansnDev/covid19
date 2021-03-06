package de.simon.covid19.android.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.simon.covid19.android.R
import de.simon.covid19.android.ui.themes.WhiteSmoke
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.GlobalSummary
import java.text.NumberFormat
import java.util.*

@Composable
fun GlobalStatisticView(countrySummary: CountrySummary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SingleGlobalStatisticView(
            Modifier.weight(1f),
            countrySummary.totalDeaths,
            countrySummary.newDeaths,
            MaterialTheme.typography.subtitle1,
            R.drawable.skull,
            28.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        SingleGlobalStatisticView(
            Modifier.weight(1.5f),
            countrySummary.totalConfirmed,
            countrySummary.newConfirmed,
            MaterialTheme.typography.h5,
            R.drawable.virus,
            32.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        SingleGlobalStatisticView(
            Modifier.weight(1f),
            countrySummary.totalRecovered,
            countrySummary.newRecovered,
            MaterialTheme.typography.subtitle1,
            R.drawable.virus_shield,
            28.dp
        )
    }
}

@Composable
fun GlobalStatisticView(globalSummary: GlobalSummary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SingleGlobalStatisticView(
            Modifier.weight(1f),
            globalSummary.totalDeaths,
            globalSummary.newDeaths,
            MaterialTheme.typography.subtitle1,
            R.drawable.skull,
            28.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        SingleGlobalStatisticView(
            Modifier.weight(1.5f),
            globalSummary.totalConfirmed,
            globalSummary.newConfirmed,
            MaterialTheme.typography.h5,
            R.drawable.virus,
            32.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        SingleGlobalStatisticView(
            Modifier.weight(1f),
            globalSummary.totalRecovered,
            globalSummary.newRecovered,
            MaterialTheme.typography.subtitle1,
            R.drawable.virus_shield,
            28.dp
        )
    }
}

@Composable
fun SingleGlobalStatisticView(
    modifier: Modifier,
    total: Int,
    new: Int,
    totalTextStyle: TextStyle,
    iconResId: Int,
    iconSize: Dp
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize),
            tint = MaterialTheme.colors.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp),
            textAlign = TextAlign.Center,
            text = NumberFormat.getNumberInstance(Locale.getDefault()).format(total),
            color = MaterialTheme.colors.onPrimary,
            style = totalTextStyle
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp),
            textAlign = TextAlign.Center,
            text = "+ ${NumberFormat.getNumberInstance(Locale.getDefault()).format(new)}",
            color = WhiteSmoke,
            style = MaterialTheme.typography.subtitle2
        )
    }
}