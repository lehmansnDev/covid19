package de.simon.covid19.datalayer.database.covid19.global

import de.simon.covid19.datalayer.database.covid19.LocalDB

fun LocalDB.get(): Global {
    return globalQueries.get().executeAsOne()
}

fun LocalDB.insert(newConfirmed: Int, totalConfirmed: Int, newDeaths: Int, totalDeaths: Int, newRecovered: Int, totalRecovered: Int, date: String) {
    globalQueries.insert(
        newConfirmed,
        totalConfirmed,
        newDeaths,
        totalDeaths,
        newRecovered,
        totalRecovered,
        date
    )
}

fun LocalDB.insert(global: Global) {
    globalQueries.insert(
        global.new_confirmed,
        global.total_confirmed,
        global.new_deaths,
        global.total_deaths,
        global.new_recovered,
        global.total_recovered,
        global.date
    )
}