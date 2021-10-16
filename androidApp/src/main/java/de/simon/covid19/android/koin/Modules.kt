package de.simon.covid19.android.koin

import de.simon.covid19.android.viewModels.DetailViewModel
import de.simon.covid19.android.viewModels.HomeViewModel
import de.simon.covid19.mapper.CountryDetailMapper
import de.simon.covid19.mapper.CountryMapper
import de.simon.covid19.mapper.Covid19Mapper
import de.simon.covid19.mapper.GlobalMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapperModule = module {
    single { GlobalMapper() }
    single { CountryMapper() }
    single { Covid19Mapper(get(), get()) }
    single { CountryDetailMapper() }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { (countryCode: String) -> DetailViewModel(countryCode, get()) }
}