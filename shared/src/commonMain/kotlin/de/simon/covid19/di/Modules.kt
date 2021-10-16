package de.simon.covid19.di

import de.simon.covid19.network.Covid19Api
import de.simon.covid19.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val commonModule: Module = module {
    single { Covid19Api() }
    single { Repository() }
}