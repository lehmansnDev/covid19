package de.simon.covid19.services

import de.simon.covid19.models.CountrySummary
import de.simon.covid19.models.Covid19SummaryDTO
import kotlinx.datetime.LocalDateTime

actual class PreferencesService {

    actual companion object {
        internal actual val COVID_19_STATISTIC_KEY = "COVID_19_STATISTIC_KEY"
        internal actual val LAST_UPDATE_KEY = "LAST_UPDATE_KEY"
    }

    actual fun getLastUpdate(): LocalDateTime {
        TODO("Not yet implemented")
    }

    actual fun getCovid19Summary(): Covid19SummaryDTO? {
        TODO("Not yet implemented")
    }

    actual fun setCurrentCovid19Statistic(covid19SummaryDTO: Covid19SummaryDTO) {
    }

    actual fun getCountrySummary(countryCode: String): CountrySummary {
        TODO("Not yet implemented")
    }
}