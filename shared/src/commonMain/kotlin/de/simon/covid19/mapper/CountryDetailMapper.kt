package de.simon.covid19.mapper

import android.util.Log
import de.simon.covid19.Constants
import de.simon.covid19.models.CountryDetailDTO
import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.StatisticHistory
import de.simon.covid19.models.StatisticValue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.math.min


class CountryDetailMapper : Mapper<List<CountryDetailDTO>, CountryDetails> {

    companion object {
        private val TAG = CountryDetailMapper::class.simpleName
    }

    override fun map(input: List<CountryDetailDTO>): CountryDetails {
        val initialDetailDTO = input
            .takeLast(Constants.DaysOfDetails)
            .first()

        var lastConfirmedValue = initialDetailDTO.confirmed
        val confirmedHistory = mutableListOf<StatisticValue>()
        var confirmedMax = 0
        var confirmedMin = Int.MAX_VALUE
        var lastDeathsValue = initialDetailDTO.deaths
        val deathsHistory = mutableListOf<StatisticValue>()
        var deathsMax = 0
        var deathsMin = Int.MAX_VALUE
        var lastRecoveredValue = initialDetailDTO.recovered
        val recoveredHistory = mutableListOf<StatisticValue>()
        var recoveredMax = 0
        var recoveredMin = Int.MAX_VALUE

        val relevantInput = input
            // And always the same number
            .takeLast(Constants.DaysOfDetails - 1)
        val firstDate =
            LocalDateTime.parse(relevantInput.first().date, DateTimeFormatter.ISO_DATE_TIME)

        relevantInput
            .forEach {
                val date = LocalDateTime.parse(it.date, DateTimeFormatter.ISO_DATE_TIME)
                // Confirmed
                val confirmedDiff = it.confirmed - lastConfirmedValue
                confirmedMax = max(confirmedDiff, confirmedMax)
                confirmedMin = min(confirmedDiff, confirmedMin)
                confirmedHistory.add(StatisticValue(confirmedDiff, date))
                lastConfirmedValue = it.confirmed
                // Deaths
                val deathsDiff = it.deaths - lastDeathsValue
                deathsMax = max(deathsDiff, deathsMax)
                deathsMin = min(deathsDiff, deathsMin)
                deathsHistory.add(StatisticValue(deathsDiff, date))
                lastDeathsValue = it.deaths
                // Recovered
                val recoveredDiff = it.recovered - lastRecoveredValue
                recoveredMax = max(recoveredDiff, recoveredMax)
                recoveredMin = min(recoveredDiff, recoveredMin)
                recoveredHistory.add(StatisticValue(recoveredDiff, date))
                lastRecoveredValue = it.recovered
            }

        Log.d(TAG, "Confirmed history values: ${confirmedHistory.size}")
        return CountryDetails(
            isEmpty = false,
            StatisticHistory(confirmedMin, confirmedMax, firstDate, confirmedHistory),
            StatisticHistory(deathsMin, deathsMax, firstDate, deathsHistory),
            StatisticHistory(recoveredMin, recoveredMax, firstDate, recoveredHistory)
        )
    }
}