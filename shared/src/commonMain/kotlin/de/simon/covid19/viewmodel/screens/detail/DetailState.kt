package de.simon.covid19.viewmodel.screens.detail

import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.viewmodel.screens.ScreenState

data class DetailState(
    val loading: Boolean,
    val failed: Boolean,
    val countrySummary: CountrySummary,
    val countryDetails: CountryDetails,
) : ScreenState {
    companion object {
        val LOADING = DetailState(
            loading = true,
            failed = false,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
        val ERROR = DetailState(
            loading = false,
            failed = true,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
    }
}