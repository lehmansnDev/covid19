package de.simon.covid19.mapper

import de.simon.covid19.models.CountryDTO
import de.simon.covid19.models.CountrySummary
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class CountryMapper {
    fun map(index: Int, input: CountryDTO): CountrySummary {
        return CountrySummary(
            input.country,
            input.countryCode,
            index,
            flagUrl = "https://www.countryflags.io/${input.countryCode}/flat/64.png",
            input.newConfirmed,
            input.totalConfirmed,
            input.newDeaths,
            input.totalDeaths,
            input.newRecovered,
            input.totalRecovered,
            Instant.parse(input.date).toLocalDateTime(TimeZone.UTC)
        )
    }

    fun map(input: CountryDTO): CountrySummary {
        return CountrySummary(
            input.country,
            input.countryCode,
            -1, /* Not needed in details */
            flagUrl = "https://www.countryflags.io/${input.countryCode}/flat/64.png",
            input.newConfirmed,
            input.totalConfirmed,
            input.newDeaths,
            input.totalDeaths,
            input.newRecovered,
            input.totalRecovered,
            Instant.parse(input.date).toLocalDateTime(TimeZone.UTC)
        )
    }
}