package de.simon.covid19.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Covid19Summary(
    val global: GlobalSummary,
    val countries: List<CountrySummary>,
    val date: LocalDateTime?,
    val dateInstant: Instant?,
    val isEmpty: Boolean
) {
    companion object {
        val EMPTY =
            Covid19Summary(
                GlobalSummary.EMPTY,
                listOf(),
                null,
                null,
                true
            )
    }
}

@Serializable
data class GlobalSummary(
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val date: LocalDateTime?
) {
    companion object {
        val EMPTY = GlobalSummary(
            0, 0, 0, 0, 0, 0,
            null
        )
    }
}

@Serializable
data class CountrySummary(
    val country: String,
    val countryCode: String,
    val index: Int,
    val flagUrl: String,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val date: LocalDateTime?
) {
    companion object {
        val EMPTY = CountrySummary(
            "", "", -1, "", 0, 0, 0, 0, 0, 0,
            null
        )
    }
}

data class CountryDetails(
    val isEmpty: Boolean,
    val confirmedHistory: StatisticHistory,
    val deathsHistory: StatisticHistory,
    val recoveredHistory: StatisticHistory,
) {
    companion object {
        val EMPTY = CountryDetails(
            true,
            StatisticHistory.EMPTY,
            StatisticHistory.EMPTY,
            StatisticHistory.EMPTY
        )
    }
}

data class StatisticHistory(
    val min: Int,
    val max: Int,
    val firstDate: LocalDateTime?,
    val history: List<StatisticValue>
) {
    companion object {
        val EMPTY = StatisticHistory(0, 0, null, listOf())
    }
}

data class StatisticValue(
    val value: Int,
    val date: LocalDateTime
)
