package de.simon.covid19.mapper

import de.simon.covid19.models.Covid19Summary
import de.simon.covid19.models.Covid19SummaryDTO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Covid19Mapper(
    private val globalMapper: GlobalMapper,
    private val countryMapper: CountryMapper
) : Mapper<Covid19SummaryDTO, Covid19Summary> {

    override fun map(input: Covid19SummaryDTO): Covid19Summary {
        val date = LocalDateTime.parse(input.date, DateTimeFormatter.ISO_DATE_TIME)
        val global = globalMapper.map(input.global)
        val sortedCountries = input.countries
            .sortedByDescending { it.newConfirmed }
            .sortedByDescending { it.totalConfirmed }
            .mapIndexed { index, countryDTO -> countryMapper.map(index = index + 1, countryDTO) }
        return Covid19Summary(global, sortedCountries, date, isEmpty = false)
    }
}