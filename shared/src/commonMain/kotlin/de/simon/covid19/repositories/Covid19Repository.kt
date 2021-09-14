package de.simon.covid19.repositories

import de.simon.covid19.Constants
import de.simon.covid19.extensions.isInSameHour
import de.simon.covid19.extensions.minus
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.services.NetworkService
import de.simon.covid19.services.PreferencesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.*

class Covid19Repository(
    private val networkService: NetworkService,
    private val preferencesService: PreferencesService,
    private val covid19Mapper: Covid19Mapper,
    private val countryDetailMapper: CountryDetailMapper
) {

    suspend fun getTodaySummary(): Covid19Summary = withContext(Dispatchers.Default) {
        try {
            val lastUpdate = preferencesService.getLastUpdate()
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            // Log.d(TAG, "Last update: $lastUpdate")
            val currentCovid19SummeryDTO = if (now.isInSameHour(lastUpdate)) {
                // The summary can not be null at this point
                // Log.d(TAG, "Loaded from preferencesService")
                preferencesService.getCovid19Summary()!!
            } else {
                // Log.d(TAG, "Loaded from networkService")
                val loadedSummery = networkService.getCovid19Summary()
                preferencesService.setCurrentCovid19Statistic(loadedSummery)
                loadedSummery
            }
            // Log.d(TAG, "Summary date: ${currentCovid19SummeryDTO.date}")
            covid19Mapper.map(currentCovid19SummeryDTO)
        } catch (e: Exception) {
            // Log.e(TAG, e.message ?: "getCurrentCovid19Statistic exception")
            val lastCovid19SummeryDTO = preferencesService.getCovid19Summary()

            if (lastCovid19SummeryDTO != null) {
                // Log.d(TAG, "Server error: last summary used ${lastCovid19SummeryDTO.date}")
                covid19Mapper.map(lastCovid19SummeryDTO)
            } else {
                Covid19Summary.EMPTY
            }
        }
    }

    suspend fun getCountrySummary(countryCode: String): CountrySummary =
        withContext(Dispatchers.Default) {
            preferencesService.getCountrySummary(countryCode)
        }

    suspend fun getCountryDetails(countryCode: String): CountryDetails =
        withContext(Dispatchers.Default) {
            try {
                val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                val toDate = LocalDateTime(now.year, now.month, now.dayOfMonth, 0, 0, 0)
                val fromDate = toDate.minus(DateTimePeriod(days = Constants.DaysOfDetails), TimeZone.UTC)

                val countryDetailDTOs = networkService.getCountryDetails(countryCode, fromDate.toString(), toDate.toString())
                countryDetailMapper.map(countryDetailDTOs)
            } catch (e: Exception) {
                // Log.e(TAG, e.message ?: "getCountryDetails exception")
                CountryDetails.EMPTY
            }
        }
}