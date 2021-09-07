package de.simon.covid19.mapper

import de.simon.covid19.models.CountryDTO
import de.simon.covid19.models.CountrySummary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            LocalDateTime.parse(input.date, DateTimeFormatter.ISO_DATE_TIME)
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
            LocalDateTime.parse(input.date, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}