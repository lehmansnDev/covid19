package de.simon.covid19.android.koin

import de.simon.covid19.android.viewModels.DetailViewModel
import de.simon.covid19.android.viewModels.HomeViewModel
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.mapper.GlobalMapper
import de.simon.covid19.repositories.Covid19Repository
import de.simon.covid19.services.NetworkService
import de.simon.covid19.services.PreferencesService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapperModule = module {
    single { GlobalMapper() }
    single { CountryMapper() }
    single { Covid19Mapper(get(), get()) }
    single { CountryDetailMapper() }
}

val serviceModule = module {
    single { NetworkService() }
    single { PreferencesService(get(), get()) }
    single { Covid19Repository(get(), get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { (countryCode: String) -> DetailViewModel(countryCode, get()) }
}