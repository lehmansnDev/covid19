package de.simon.covid19.database.countries

import de.simon.covid19.database.LocalDB
import de.simon.covid19.database.countries.Country
import de.simon.covid19.models.CountryDTO

fun LocalDB.getAllCountries(): List<CountryDTO> {
    return countriesQueries.getAll().executeAsList().map { country ->
        CountryDTO(
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

fun LocalDB.getCountry(countryCode: String): CountryDTO? {
    val country = countriesQueries.getByCountryCode(countryCode).executeAsOneOrNull() ?: return null

    return CountryDTO(
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

fun LocalDB.insert(countries: List<CountryDTO>) {
    countriesQueries.transaction {
        countries.forEach { countryDTO ->
            countriesQueries.insert(
                countryDTO.id,
                countryDTO.name,
                countryDTO.countryCode,
                countryDTO.slug,
                countryDTO.newConfirmed,
                countryDTO.totalConfirmed,
                countryDTO.newDeaths,
                countryDTO.totalDeaths,
                countryDTO.newRecovered,
                countryDTO.totalRecovered,
                countryDTO.date
            )
        }
    }
}