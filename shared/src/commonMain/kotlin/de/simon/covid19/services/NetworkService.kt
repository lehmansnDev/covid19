package de.simon.covid19.services

import de.simon.covid19.models.CountryDetailDTO
import de.simon.covid19.models.Covid19SummaryDTO
import de.simon.covid19.network.KtorClient
import io.ktor.client.*
import io.ktor.client.request.*

class NetworkService {

    private var httpClient: HttpClient

    init {
        val ktor = KtorClient()
        httpClient = ktor.createClient()
    }

    suspend fun getCovid19Summary(): Covid19SummaryDTO {
        return httpClient.get("https://api.covid19api.com/summary")
    }

    suspend fun getCountryDetails(
        countryCode: String,
        from: String,
        to: String
    ): List<CountryDetailDTO> {
        // Log.d(TAG, "$countryCode: $from - $to")
        return httpClient.get("https://api.covid19api.com/total/country/$countryCode?from=$from&to=$to")
    }
}