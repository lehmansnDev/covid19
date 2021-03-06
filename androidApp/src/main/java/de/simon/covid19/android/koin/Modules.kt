package de.simon.covid19.android.koin

import de.simon.covid19.android.viewModels.DetailViewModel
import de.simon.covid19.android.viewModels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { (countryCode: String) -> DetailViewModel(countryCode, get()) }
}