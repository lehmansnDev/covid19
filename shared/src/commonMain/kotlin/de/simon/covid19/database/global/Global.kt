package de.simon.covid19.database.global

import de.simon.covid19.database.LocalDB
import de.simon.covid19.models.GlobalDTO

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

fun LocalDB.insert(globalDTO: GlobalDTO) {
    globalQueries.insert(
        globalDTO.newConfirmed,
        globalDTO.totalConfirmed,
        globalDTO.newDeaths,
        globalDTO.totalDeaths,
        globalDTO.newRecovered,
        globalDTO.totalRecovered,
        globalDTO.date
    )
}