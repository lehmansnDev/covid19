
object Versions {
    const val kotlin = "1.5.30"
    const val gradle = "7.0.3"
    const val sql_delight = "1.5.2"

    const val compose = "1.1.0-alpha06"
    const val constraint_layout_compose = "1.0.0-rc01"
    const val nav_compose = "2.4.0-alpha10"
    const val activity_compose = "1.3.1"
    const val coil_compose = "1.3.2"

    const val insets_accompanist = "0.18.0"

    const val koin = "3.1.2"
    const val koin_ext = "3.0.1-beta-1"

    const val kotlinx_datetime = "0.3.0"
    const val kotlinx_serialization = "1.2.2"
    const val kotlinx_coroutines = "1.5.2-native-mt"

    const val ktor = "1.6.4"

    const val lighthouse_logging = "1.0.0"
}

object Compose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraint_layout_compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val activity = "androidx.navigation:navigation-compose:${Versions.activity_compose}"
    const val coil = "io.coil-kt:coil-compose:${Versions.coil_compose}"
}

object Accompanist {
    const val insets = "com.google.accompanist:accompanist-insets:${Versions.insets_accompanist}"
}

object Koin {

    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val ext = "io.insert-koin:koin-android-ext:${Versions.koin_ext}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object Kotlinx {
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinx_datetime}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
}

object Ktor {
    const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val json = "io.ktor:ktor-client-json:${Versions.ktor}"
    const val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val android = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
}

object SqlDelight {
    const val runtime = "com.squareup.sqldelight:runtime:${Versions.sql_delight}"
    const val android  = "com.squareup.sqldelight:android-driver:${Versions.sql_delight}"
    const val native = "com.squareup.sqldelight:native-driver:${Versions.sql_delight}"
}

object Lighthouse {
    const val logging = "org.lighthousegames:logging:${Versions.lighthouse_logging}"
}