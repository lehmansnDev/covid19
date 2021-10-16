package de.simon.covid19.datalayer.database.covid19.countries

import de.simon.covid19.datalayer.database.covid19.LocalDB

fun LocalDB.getAll(): List<Country> {
    return countriesQueries.getAll().executeAsList()
}

fun LocalDB.getById(id: String): Country {
    return countriesQueries.getById(id).executeAsOne();
}

fun LocalDB.insert(countries: List<Country>) {
    countriesQueries.transaction {
        countries.forEach { country ->
            countriesQueries.insert(
                country.id,
                country.name,
                country.country_code,
                country.slug,
                country.new_confirmed,
                country.total_confirmed,
                country.new_deaths,
                country.total_deaths,
                country.new_recovered,
                country.total_recovered,
                country.date
            )
        }
    }
}