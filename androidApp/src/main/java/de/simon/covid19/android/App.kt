package de.simon.covid19.android

import android.app.Application
import de.simon.covid19.android.koin.viewModelModule
import de.simon.covid19.di.commonModule
import de.simon.covid19.di.mapperModule
import de.simon.covid19.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                mapperModule,
                viewModelModule,
                commonModule,
                platformModule()
            )
        }
    }
}