package de.simon.covid19.database

import de.simon.covid19.datalayer.database.covid19.LocalDB

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(LocalDB.Schema, "covid19.db")
    }
}