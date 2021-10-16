package de.simon.covid19.viewModels.states

import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary

data class DetailState(
    val loading: Boolean,
    val failed: Boolean,
    val countrySummary: CountrySummary,
    val countryDetails: CountryDetails,
) {
    companion object {
        val LOADING = DetailState(
            loading = true,
            failed = false,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
        val FAILED = DetailState(
            loading = false,
            failed = true,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
    }
}