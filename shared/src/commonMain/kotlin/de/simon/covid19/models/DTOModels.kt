package de.simon.covid19.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Covid19SummaryDTO(
    @SerialName("ID")
    val id: String,
    @SerialName("Message")
    val message: String,
    @SerialName("Global")
    val global: GlobalDTO,
    @SerialName("Countries")
    val countries: List<CountryDTO>,
    @SerialName("Date")
    val date: String
)

@Serializable
data class GlobalDTO(
    @SerialName("NewConfirmed")
    val newConfirmed: Int,
    @SerialName("TotalConfirmed")
    val totalConfirmed: Int,
    @SerialName("NewDeaths")
    val newDeaths: Int,
    @SerialName("TotalDeaths")
    val totalDeaths: Int,
    @SerialName("NewRecovered")
    val newRecovered: Int,
    @SerialName("TotalRecovered")
    val totalRecovered: Int,
    @SerialName("Date")
    val date: String
)

@Serializable
data class CountryDTO(
    @SerialName("ID")
    val id: String,
    @SerialName("Country")
    val name: String,
    @SerialName("CountryCode")
    val countryCode: String,
    @SerialName("Slug")
    val slug: String,
    @SerialName("NewConfirmed")
    val newConfirmed: Int,
    @SerialName("TotalConfirmed")
    val totalConfirmed: Int,
    @SerialName("NewDeaths")
    val newDeaths: Int,
    @SerialName("TotalDeaths")
    val totalDeaths: Int,
    @SerialName("NewRecovered")
    val newRecovered: Int,
    @SerialName("TotalRecovered")
    val totalRecovered: Int,
    @SerialName("Date")
    val date: String
)

@Serializable
data class CountryDetailDTO(
    @SerialName("Country")
    val country: String,
    @SerialName("CountryCode")
    val countryCode: String,
    @SerialName("Province")
    val province: String,
    @SerialName("City")
    val city: String,
    @SerialName("CityCode")
    val cityCode: String,
    @SerialName("Lat")
    val lat: String,
    @SerialName("Lon")
    val lon: String,
    @SerialName("Confirmed")
    val confirmed: Int,
    @SerialName("Deaths")
    val deaths: Int,
    @SerialName("Recovered")
    val recovered: Int,
    @SerialName("Active")
    val active: Int,
    @SerialName("Date")
    val date: String
)