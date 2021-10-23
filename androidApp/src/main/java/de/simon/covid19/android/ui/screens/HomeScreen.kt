package de.simon.covid19.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsHeight
import de.simon.covid19.android.R
import de.simon.covid19.android.ui.themes.LightGray
import de.simon.covid19.android.ui.themes.WhiteSmoke
import de.simon.covid19.android.ui.views.CountryListView
import de.simon.covid19.android.ui.views.GlobalStatisticView
import de.simon.covid19.android.viewModels.HomeViewModel
import de.simon.covid19.android.viewModels.actions.HomeAction
import de.simon.covid19.models.GlobalSummary
import kotlinx.datetime.toJavaLocalDateTime
import org.koin.androidx.compose.getViewModel
import java.text.DateFormat
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt

@Composable
fun HomeScreen(selectCountry: (String) -> Unit, viewModel: HomeViewModel = getViewModel()) {

    val viewState = viewModel.viewState.collectAsState()

    Column(
        Modifier.background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (viewState.value.loading) {
            // On loading
            FullscreenGradientBox {
                Icon(
                    painter = painterResource(id = R.drawable.virus),
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp),
                    tint = Color.White,
                )
            }
        } else {
            if (viewState.value.failed) {
                // No data available
                FullscreenGradientBox {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.covid_api_not_available),
                        color = WhiteSmoke,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            } else {
                val offsetY = 24.dp
                // Data available
                GlobalSummary(
                    viewState.value.globalSummary,
                    input = viewState.value.input,
                    onInputChanged = { viewModel.onAction(HomeAction.InputChanged(it)) },
                    onInputDelete = { viewModel.onAction(HomeAction.InputDelete) })
                LazyColumn(
                    Modifier
                        .offset(y = -offsetY)
                        .padding(8.dp, 0.dp)
                        .zIndex(-1.0f),
                    contentPadding = PaddingValues(
                        0.dp, offsetY, 0.dp, rememberInsetsPaddingValues(
                            LocalWindowInsets.current.navigationBars
                        ).calculateBottomPadding()
                    )
                ) {
                    items(items = viewState.value.filteredCountries) { countries ->
                        CountryListView(country = countries, selectCountry = selectCountry)
                    }
                }
//                OffsetLazyColumn(
//                    modifier = Modifier.fillMaxHeight()
//                        .zIndex(-1.0f),
//                    contentPadding = PaddingValues(
//                        0.dp, offsetY, 0.dp, rememberInsetsPaddingValues(
//                            LocalWindowInsets.current.navigationBars
//                        ).calculateBottomPadding()
//                    ),
//                    offsetY = offsetY,
//                ) {
//                    items(items = viewState.value.filteredCountries) { countries ->
//                        CountryListView(country = countries, selectCountry = selectCountry)
//                    }
//                }
            }
        }
    }
}

@Composable
fun OffsetLazyColumn(
    modifier: Modifier = Modifier,
    offsetY: Dp,
    contentPadding: PaddingValues,
    content: LazyListScope.() -> Unit
) {
    val offsetYInPixel = LocalDensity.current.run { offsetY.roundToPx() }

    Layout(
        modifier = modifier,
        content = {
            LazyColumn(
                modifier = Modifier.padding(8.dp, 0.dp),
                content = content,
                contentPadding = contentPadding
            )
        }) { measurables, constraints ->
        val column = measurables[0].measure(constraints)
        val offset = offsetY.roundToPx()
        layout(constraints.maxWidth, constraints.maxHeight + offset) {
            column.placeRelative(IntOffset(0, -(offset)))
        }
    }
}

@Composable
fun FullscreenGradientBox(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
                    Offset.Zero,
                    Offset.Infinite,
                )
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun GlobalSummary(
    global: GlobalSummary,
    input: String,
    onInputChanged: (String) -> Unit,
    onInputDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp),
    ) {
        Column(
            Modifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
                        Offset.Zero,
                        Offset.Infinite,
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.statusBarsHeight())
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                textAlign = TextAlign.Center,
                text = DateFormat.getDateTimeInstance()
                    .format(
                        Date.from(
                            global.date!!.toJavaLocalDateTime()
                                .atZone(ZoneId.systemDefault()).toInstant()
                        )
                    ),
                color = WhiteSmoke,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlobalStatisticView(
                    Modifier.weight(1f),
                    global.totalDeaths,
                    global.newDeaths,
                    MaterialTheme.typography.subtitle1,
                    R.drawable.skull,
                    28.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                GlobalStatisticView(
                    Modifier.weight(1.5f),
                    global.totalConfirmed,
                    global.newConfirmed,
                    MaterialTheme.typography.h5,
                    R.drawable.virus,
                    32.dp
                )
                Spacer(modifier = Modifier.width(16.dp))
                GlobalStatisticView(
                    Modifier.weight(1f),
                    global.totalRecovered,
                    global.newRecovered,
                    MaterialTheme.typography.subtitle1,
                    R.drawable.virus_shield,
                    28.dp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            CountrySearchField(input, onInputChanged, onInputDelete)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun CountrySearchField(
    input: String,
    onInputChanged: (String) -> Unit,
    onInputDelete: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        val iconSize = 24.dp

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .defaultMinSize(iconSize)
                    .size(iconSize),
                tint = if (input.isEmpty()) LightGray else Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            SearchTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp), input, onInputChanged
            )
            if (input.isNotEmpty()) {
                IconButton(onClick = onInputDelete) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        modifier = Modifier
                            .defaultMinSize(iconSize)
                            .size(iconSize),
                        tint = Color.White,
                    )
                }
            } else {
                // Fill space if icon is not visible
                Spacer(modifier = Modifier.width(iconSize + 24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}


@Composable
fun SearchTextField(modifier: Modifier, input: String, onInputChanged: (String) -> Unit) {
    var textFieldFocused by remember { mutableStateOf(false) }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        BasicTextField(
            modifier = Modifier.onFocusChanged { focusState ->
                textFieldFocused = focusState.hasFocus
            },
            value = input,
            onValueChange = onInputChanged,
            maxLines = 1,
            textStyle = MaterialTheme.typography.subtitle1.copy(color = Color.White),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            cursorBrush = SolidColor(
                Color.White
            )
        )
        if (input.isEmpty() && !textFieldFocused) {
            Text(
                text = stringResource(id = R.string.search_country),
                style = MaterialTheme.typography.subtitle1,
                color = LightGray
            )
        }
    }

}