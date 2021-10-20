package de.simon.covid19.di

import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.mapper.GlobalMapper
import de.simon.covid19.network.Covid19Api
import de.simon.covid19.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val commonModule: Module = module {
    single { Covid19Api() }
    single { Repository() }
}

val mapperModule = module {
    single { GlobalMapper() }
    single { CountryMapper() }
    single { Covid19Mapper(get(), get()) }
    single { CountryDetailMapper() }
}
