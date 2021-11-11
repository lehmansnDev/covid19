package de.simon.covid19.viewModels.states

import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.GlobalSummary

data class HomeState(
    val type: StateType,
    val globalSummary: GlobalSummary,
    val filteredCountries: List<CountrySummary>,
    val input: String
) {
    companion object {
        val LOADING = HomeState(
            type = StateType.LOADING,
            globalSummary = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
        val FAILED = HomeState(
            type = StateType.FAILED,
            globalSummary = Covid19Summary.EMPTY.global,
            filteredCountries = listOf(),
            input = ""
        )
    }
}