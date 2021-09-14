package de.simon.covid19.mapper

import de.simon.covid19.models.GlobalDTO
import de.simon.covid19.models.GlobalSummary
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GlobalMapper : Mapper<GlobalDTO, GlobalSummary> {

    override fun map(input: GlobalDTO): GlobalSummary {
        return GlobalSummary(
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