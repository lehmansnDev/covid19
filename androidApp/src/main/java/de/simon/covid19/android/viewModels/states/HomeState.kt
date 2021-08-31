package de.simon.covid19.android.viewModels.states

import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.GlobalSummary

data class HomeState(
    val isLoading: Boolean,
    val isEmpty: Boolean,
    val global: GlobalSummary,
    val filteredCountries: List<CountrySummary>,
    val input: String
) {
    companion object {
        val LOADING = HomeState(
            isLoading = true,
            isEmpty = true,
            global = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
        val ERROR = HomeState(
            isLoading = false,
            isEmpty = true,
            global = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
    }
}