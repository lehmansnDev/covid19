package de.simon.covid19.android

import android.app.Application
import de.simon.covid19.android.koin.mapperModule
import de.simon.covid19.android.koin.serviceModule
import de.simon.covid19.android.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    mapperModule,
                    serviceModule,
                    viewModelModule
                ))
        }
    }
}