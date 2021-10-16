package de.simon.covid19.repository

import de.simon.covid19.database.DatabaseDriverFactory
import de.simon.covid19.database.LocalDB
import de.simon.covid19.database.countries.getAllCountries
import de.simon.covid19.database.countries.getCountry
import de.simon.covid19.database.countries.insert
import de.simon.covid19.database.global.getGlobal
import de.simon.covid19.database.global.insert
import de.simon.covid19.extensions.isInSameHour
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.Covid19SummaryDTO
import de.simon.covid19.network.Covid19Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent

class Repository(
    databaseDriverFactory: DatabaseDriverFactory,
    private val covid19Api: Covid19Api,
    private val covid19Mapper: Covid19Mapper,
    private val countryDetailMapper: CountryDetailMapper,
    private val countryMapper: CountryMapper
): KoinComponent {

    private val localDB = LocalDB.invoke(databaseDriverFactory.createDriver())

    suspend fun getCovid19Summary(): Covid19Summary = withContext(Dispatchers.Default) {

        val lastGlobal = localDB.getGlobal()

        if (lastGlobal != null) {
            val lastUpdate = Instant.parse(lastGlobal.date).toLocalDateTime(TimeZone.UTC)
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            if (now.isInSameHour(lastUpdate)) {
                return@withContext getStoredSummary()
            }
        }
        // If lastUpdate is null or older than an hour
        try {
            val summaryDTO = covid19Api.fetchCovid19Summary()
            return@withContext storeSummary(summaryDTO)
        } catch (e: Exception) {
            if(lastGlobal != null) {
                getStoredSummary()
            } else {
                Covid19Summary.EMPTY
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

    suspend fun getCountrySummary(countryCode: String): CountrySummary = withContext(Dispatchers.Default) {
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
