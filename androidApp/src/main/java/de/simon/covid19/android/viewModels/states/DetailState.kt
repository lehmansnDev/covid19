package de.simon.covid19.android.viewModels.states

import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary

data class DetailState(
    val isLoading: Boolean,
    val isEmpty: Boolean,
    val countrySummary: CountrySummary,
    val countryDetails: CountryDetails,
) {
    companion object {
        val LOADING = DetailState(
            isLoading = true,
            isEmpty = true,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
        val ERROR = DetailState(
            isLoading = false,
            isEmpty = true,
            countrySummary = CountrySummary.EMPTY,
            countryDetails = CountryDetails.EMPTY
        )
    }
}