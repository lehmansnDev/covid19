package de.simon.covid19.repositories

import android.util.Log
import de.simon.covid19.Constants
import de.simon.covid19.extensions.isInSameHour
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.services.NetworkService
import de.simon.covid19.services.PreferencesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Covid19Repository(
    private val networkService: NetworkService,
    private val preferencesService: PreferencesService,
    private val covid19Mapper: Covid19Mapper,
    private val countryDetailMapper: CountryDetailMapper
) {

    companion object {
        private val TAG = Covid19Repository::class.simpleName
    }

    suspend fun getTodaySummary(): Covid19Summary = withContext(Dispatchers.Default) {
        try {
            val lastUpdate = preferencesService.getLastUpdate()
            Log.d(TAG, "Last update: ${lastUpdate.format(DateTimeFormatter.ISO_DATE_TIME)}")
            val currentCovid19SummeryDTO = if (LocalDateTime.now().isInSameHour(lastUpdate)) {
                // The summary can not be null at this point
                Log.d(TAG, "Loaded from preferencesService")
                preferencesService.getCovid19Summary()!!
            } else {
                Log.d(TAG, "Loaded from networkService")
                val loadedSummery = networkService.getCovid19Summary()
                preferencesService.setCurrentCovid19Statistic(loadedSummery)
                loadedSummery
            }
            Log.d(TAG, "Summary date: ${currentCovid19SummeryDTO.date}")
            covid19Mapper.map(currentCovid19SummeryDTO)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "getCurrentCovid19Statistic exception")
            val lastCovid19SummeryDTO = preferencesService.getCovid19Summary()

            if (lastCovid19SummeryDTO != null) {
                Log.d(TAG, "Server error: last summary used ${lastCovid19SummeryDTO.date}")
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
                val toDate = LocalDateTime.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    LocalDate.now().getDayOfMonth(),
                    0,
                    0,
                    0
                )
                val fromDate = toDate.minusDays(Constants.DaysOfDetails.toLong())

                val to = toDate.format(DateTimeFormatter.ISO_DATE_TIME)
                val from = fromDate.format(DateTimeFormatter.ISO_DATE_TIME)
                val countryDetailDTOs = networkService.getCountryDetails(countryCode, from, to)
                countryDetailMapper.map(countryDetailDTOs)
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "getCountryDetails exception")
                CountryDetails.EMPTY
            }
        }
}