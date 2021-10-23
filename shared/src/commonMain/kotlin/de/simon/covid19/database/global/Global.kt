package de.simon.covid19.database.global

import de.simon.covid19.database.LocalDB
import de.simon.covid19.models.GlobalDTO
import kotlinx.datetime.*

fun LocalDB.getGlobal(): GlobalDTO? {
    val global = globalQueries.get().executeAsOneOrNull() ?: return null
    return GlobalDTO(
        global.new_confirmed,
        global.total_confirmed,
        global.new_deaths,
        global.total_deaths,
        global.new_recovered,
        global.total_recovered,
        global.date
    )
}

fun LocalDB.getLastUpdate(): LocalDateTime? {
    val lastUpdate = globalQueries.lastUpdate().executeAsOneOrNull() ?: return null
    return Instant.parse(lastUpdate).toLocalDateTime(TimeZone.UTC)
}

fun LocalDB.insert(globalDTO: GlobalDTO) {
    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    var nowString = now.toString()
    if(nowString.last() != 'Z') {
        nowString = "${nowString}Z"
    }
    globalQueries.insert(
        0,
        globalDTO.newConfirmed,
        globalDTO.totalConfirmed,
        globalDTO.newDeaths,
        globalDTO.totalDeaths,
        globalDTO.newRecovered,
        globalDTO.totalRecovered,
        globalDTO.date,
        nowString
    )
}