package de.simon.covid19.di

import de.simon.covid19.database.DatabaseDriverFactory

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
}
