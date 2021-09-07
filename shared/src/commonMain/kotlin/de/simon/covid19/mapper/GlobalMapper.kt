package de.simon.covid19.mapper

import de.simon.covid19.models.GlobalDTO
import de.simon.covid19.models.GlobalSummary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GlobalMapper : Mapper<GlobalDTO, GlobalSummary> {

    override fun map(input: GlobalDTO): GlobalSummary {
        return GlobalSummary(
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