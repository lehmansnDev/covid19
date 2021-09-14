package de.simon.covid19.mapper

import de.simon.covid19.Constants
import de.simon.covid19.models.CountryDetailDTO
import de.simon.covid19.models.CountryDetails
import de.simon.covid19.models.StatisticHistory
import de.simon.covid19.models.StatisticValue
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.max
import kotlin.math.min


class CountryDetailMapper : Mapper<List<CountryDetailDTO>, CountryDetails> {
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

        val firstDate = Instant.parse(relevantInput.first().date).toLocalDateTime(TimeZone.UTC)

        relevantInput
            .forEach { countryDetailsDto ->
                val date = Instant.parse(countryDetailsDto.date).toLocalDateTime(TimeZone.UTC)
                // Confirmed
                val confirmedDiff = countryDetailsDto.confirmed - lastConfirmedValue
                confirmedMax = max(confirmedDiff, confirmedMax)
                confirmedMin = min(confirmedDiff, confirmedMin)
                confirmedHistory.add(StatisticValue(confirmedDiff, date))
                lastConfirmedValue = countryDetailsDto.confirmed
                // Deaths
                val deathsDiff = countryDetailsDto.deaths - lastDeathsValue
                deathsMax = max(deathsDiff, deathsMax)
                deathsMin = min(deathsDiff, deathsMin)
                deathsHistory.add(StatisticValue(deathsDiff, date))
                lastDeathsValue = countryDetailsDto.deaths
                // Recovered
                val recoveredDiff = countryDetailsDto.recovered - lastRecoveredValue
                recoveredMax = max(recoveredDiff, recoveredMax)
                recoveredMin = min(recoveredDiff, recoveredMin)
                recoveredHistory.add(StatisticValue(recoveredDiff, date))
                lastRecoveredValue = countryDetailsDto.recovered
            }

        return CountryDetails(
            isEmpty = false,
            StatisticHistory(confirmedMin, confirmedMax, firstDate, confirmedHistory),
            StatisticHistory(deathsMin, deathsMax, firstDate, deathsHistory),
            StatisticHistory(recoveredMin, recoveredMax, firstDate, recoveredHistory)
        )
    }
}