package de.simon.covid19.services

import android.content.Context
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19SummaryDTO
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual class PreferencesService(private val context: Context, private val countryMapper: CountryMapper) {

    actual companion object {
        internal actual val COVID_19_STATISTIC_KEY = "COVID_19_STATISTIC_KEY"
        internal actual val LAST_UPDATE_KEY = "LAST_UPDATE_KEY"
    }

    actual fun getLastUpdate(): LocalDateTime {
        val sharedPref = context.getSharedPreferences(LAST_UPDATE_KEY, Context.MODE_PRIVATE)
            ?: return LocalDateTime.MIN
        val minDateString = LocalDateTime.MIN.format(DateTimeFormatter.ISO_DATE_TIME)
        val dateString =
            sharedPref.getString(LAST_UPDATE_KEY, minDateString) ?: return LocalDateTime.MIN
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
    }

    @ExperimentalSerializationApi
    actual fun getCovid19Summary(): Covid19SummaryDTO? {
        val sharedPref = context.getSharedPreferences(COVID_19_STATISTIC_KEY, Context.MODE_PRIVATE)
            ?: return null
        val json = sharedPref.getString(COVID_19_STATISTIC_KEY, null) ?: return null
        return Json.decodeFromString<Covid19SummaryDTO>(json)
    }

    @ExperimentalSerializationApi
    actual fun setCurrentCovid19Statistic(covid19SummaryDTO: Covid19SummaryDTO) {
        val sharedPref =
            context.getSharedPreferences(COVID_19_STATISTIC_KEY, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            val json = Json.encodeToString(covid19SummaryDTO)
            putString(COVID_19_STATISTIC_KEY, json)
            apply()
        }
        setLastUpdate(LocalDateTime.now())
    }

    private fun setLastUpdate(localDateTime: LocalDateTime) {
        val sharedPref =
            context.getSharedPreferences(LAST_UPDATE_KEY, Context.MODE_PRIVATE) ?: return

        with(sharedPref.edit()) {
            putString(LAST_UPDATE_KEY, localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
            apply()
        }
    }

    actual fun getCountrySummary(countryCode: String): CountrySummary {
        val countryDTO = getCovid19Summary()!!.countries.first { it.countryCode == countryCode }
        return countryMapper.map(countryDTO)
    }


}
