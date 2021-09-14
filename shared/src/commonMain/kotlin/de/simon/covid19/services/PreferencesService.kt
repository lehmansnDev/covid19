package de.simon.covid19.services

import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19SummaryDTO
import kotlinx.datetime.LocalDateTime

expect class PreferencesService {

    companion object {
        internal val COVID_19_STATISTIC_KEY: String
        internal val LAST_UPDATE_KEY: String
    }

    fun getLastUpdate(): LocalDateTime
    fun getCovid19Summary(): Covid19SummaryDTO?
    fun setCurrentCovid19Statistic(covid19SummaryDTO: Covid19SummaryDTO)
    fun getCountrySummary(countryCode: String): CountrySummary
}