package de.simon.covid19.viewmodel.screens.home

import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.GlobalSummary
import de.simon.covid19.viewmodel.screens.ScreenState

data class HomeState(
    val loading: Boolean,
    val failed: Boolean,
    val globalSummary: GlobalSummary,
    val filteredCountries: List<CountrySummary>,
    val input: String
) : ScreenState {
    companion object {
        val LOADING = HomeState(
            loading = true,
            failed = false,
            globalSummary = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
        val FAILED = HomeState(
            loading = false,
            failed = true,
            globalSummary = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
    }
}