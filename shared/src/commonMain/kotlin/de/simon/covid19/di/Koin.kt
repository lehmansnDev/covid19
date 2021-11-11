package de.simon.covid19.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(platformModule(), commonModule, mapperModule)
    }

// called by iOS etc
fun initKoin() = initKoin {}

