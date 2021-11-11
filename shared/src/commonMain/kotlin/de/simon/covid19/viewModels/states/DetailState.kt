package de.simon.covid19.viewModels.states

import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary

data class DetailState(
    val type: StateType,
    val countrySummary: CountrySummary,
    val countryDetails: CountryDetails,
) {
    companion object {
        val LOADING = DetailState(
            type = StateType.LOADING,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
        val FAILED = DetailState(
            type = StateType.FAILED,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
    }
}