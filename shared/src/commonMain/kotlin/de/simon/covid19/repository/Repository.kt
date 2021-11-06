package de.simon.covid19.repository

import de.simon.covid19.database.DatabaseDriverFactory
import de.simon.covid19.database.LocalDB
import de.simon.covid19.database.countries.getAllCountries
import de.simon.covid19.database.countries.getCountry
import de.simon.covid19.database.countries.insert
import de.simon.covid19.database.global.getGlobal
import de.simon.covid19.database.global.getLastUpdate
import de.simon.covid19.database.global.insert
import de.simon.covid19.extensions.isInSameHour
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.Covid19SummaryDTO
import de.simon.covid19.network.Covid19Api
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class Repository: KoinComponent {

    companion object {
        val log = logging()     // or can instantiate KmLog()
    }

    private val databaseDriverFactory: DatabaseDriverFactory by inject()
    private val covid19Api: Covid19Api by inject()
    private val covid19Mapper: Covid19Mapper by inject()
    private val countryDetailMapper: CountryDetailMapper by inject()
    private val countryMapper: CountryMapper by inject()

    private val localDB = LocalDB.invoke(databaseDriverFactory.createDriver())

    suspend fun getCovid19Summary(): Covid19Summary = withContext(Dispatchers.Main) {

        val lastUpdate = localDB.getLastUpdate()
        log.d { "Last update is null: ${lastUpdate == null}" }
        if (lastUpdate != null) {
            log.d { "Check timestamp of last data..." }
            val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            if (now.isInSameHour(lastUpdate)) {
                log.d { "Local data is new - Use data from database" }
                return@withContext getStoredSummary()
            }
            else {
                log.d { "Local data is old..." }
            }
        }
        // If lastUpdate is null or older than an hour
        try {
            log.d { "Fetch data from api..." }
            val summaryDTO = covid19Api.fetchCovid19Summary()
            return@withContext storeSummary(summaryDTO)
        } catch (e: Exception) {
            log.e { "Exception: ${e.message}" }
            log.e { "${e.printStackTrace()}" }
            if(lastUpdate != null) {
                log.d { "Exception - use data from database" }
                return@withContext getStoredSummary()
            } else {
                log.d { "Exception - no data available use EMPTY" }
                return@withContext Covid19Summary.EMPTY
            }
        }
    }

    private fun getStoredSummary(): Covid19Summary {
        val globalDTO = localDB.getGlobal()
        val countryDTOs = localDB.getAllCountries()
        val dto = Covid19SummaryDTO("", "", globalDTO!!, countryDTOs, globalDTO.date)
        return covid19Mapper.map(dto)
    }

    private fun storeSummary(summaryDTO: Covid19SummaryDTO): Covid19Summary {
        localDB.insert(summaryDTO.global)
        localDB.insert(summaryDTO.countries)
        return covid19Mapper.map(summaryDTO)
    }

    suspend fun getCountrySummary(countryCode: String): CountrySummary = withContext(Dispatchers.Main) {
        val countryDTO = localDB.getCountry(countryCode)
        countryMapper.map(countryDTO!!)
    }

//    suspend fun getCountryDetails(countryCode: String): CountryDetails =
//        withContext(Dispatchers.Default) {
//            try {
//                val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
//                val toDate = LocalDateTime(now.year, now.month, now.dayOfMonth, 0, 0, 0)
//                val fromDate =
//                    toDate.minus(DateTimePeriod(days = Constants.DaysOfDetails), TimeZone.UTC)
//
//                val countryDetailDTOs = networkService.getCountryDetails(
//                    countryCode,
//                    fromDate.toString(),
//                    toDate.toString()
//                )
//                countryDetailMapper.map(countryDetailDTOs)
//            } catch (e: Exception) {
//                // Log.e(TAG, e.message ?: "getCountryDetails exception")
//                CountryDetails.EMPTY
//            }
//        }
}
