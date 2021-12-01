# COVID-19

This is a sample project based on KMM, Jetpack Compose and SwiftUI.  

## Screenshots

<img src="/media/screenshots.png" height="600px">

## Description
When the app starts, a check is made to see whether new Covid-19 data has already been fetched in the last hour.
If this is the case, the app uses the local data of its database. 
If this is not the case, new data is fetched via the API and saved in the local database.
This whole logic is contained in the repository.
With the search field the list of countries can be filtered and the app provides dark and light mode.
The architecture model used is MVI.  
<img src="/media/architecture.png">

## Architecture

I am more familiar with Android and Kotlin therefore I preferred an architecture with less swift code.
In my case this was the [MVI](https://medium.com/mobile-at-octopus-energy/architecture-in-jetpack-compose-mvp-mvvm-mvi-17d8170a13fd) architecture patten which allows using easy state handling with native ViewModels in Android and iOS. Besides that, the actions and states can be a part of the shared code. With MVVM I would have to write more swift code because the ViewModels would have to contain more complexity. The approach of [D-KMP](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343) allows more shared code because it shares not only ViewModels but also navigation. The problem with this architecture is the boilerplate code which is more code than the current app until now.
That is the reason why MVI is the optimal solution for the project.  
<img src="/media/mvi.png">


### Data used

Covid-19: https://covid19api.com/  
Icons: https://fontawesome.com/  
Flags: https://flagcdn.com/  

### Libraries

Accompanist: https://github.com/google/accompanist  
Koin: https://github.com/InsertKoinIO/koin  
Kotlinx: https://github.com/Kotlin/kotlinx.coroutines, https://github.com/Kotlin/kotlinx.serialization  
Ktor: https://github.com/ktorio/ktor  
SqlDelight: https://github.com/cashapp/sqldelight  
Logging: https://github.com/LighthouseGames/KmLogging  


