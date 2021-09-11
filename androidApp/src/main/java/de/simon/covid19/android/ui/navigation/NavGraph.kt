package de.simon.covid19.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.simon.covid19.android.ui.screens.DetailScreen
import de.simon.covid19.android.ui.screens.HomeScreen

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val COUNTRY_CODE_KEY = "country_code"
}

@Composable
fun NavGraph(startDestination: String = Destinations.HOME_ROUTE) {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.HOME_ROUTE) {
            HomeScreen(selectCountry = actions.selectCountry)
        }
        composable("${Destinations.DETAIL_ROUTE}/${Destinations.COUNTRY_CODE_KEY}") { backStackEntry ->
            DetailScreen(
                countryCode = backStackEntry.arguments?.getString(Destinations.COUNTRY_CODE_KEY)
                    ?: "",
                backPress = actions.backPress
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(navController: NavHostController) {
    val selectCountry: (String) -> Unit = { countryCode: String ->
        navController.navigate("${Destinations.DETAIL_ROUTE}/$countryCode")
    }
    val backPress: () -> Unit = {
        navController.navigateUp()
    }
}