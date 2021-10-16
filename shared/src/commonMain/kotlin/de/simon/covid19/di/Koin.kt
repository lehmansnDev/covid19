package de.simon.covid19.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
//        modules(platformModule(),  commonModule)
    }

// called by iOS etc
fun initKoin() = initKoin {}

