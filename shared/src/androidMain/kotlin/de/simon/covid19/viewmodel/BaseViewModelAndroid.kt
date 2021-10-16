package de.simon.covid19.viewmodel

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import de.simon.covid19.datalayer.Repository
import de.simon.covid19.datalayer.database.covid19.LocalDB

fun BaseViewModel.Factory.getAndroidInstance(context : Context) : BaseViewModel {
    val sqlDriver = AndroidSqliteDriver(LocalDB.Schema, context, "covid19.db")
    val repository = Repository(sqlDriver)
    return BaseViewModel(repository)
}