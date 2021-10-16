import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val ktorVersion = "1.6.3"
val serializationVersion = "1.2.2"
val coroutineVersion = "1.5.2"
val koinVersion = "3.1.2"
val datetimeVersion = "0.3.0"
val sqlDelightVersion = "1.5.2"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.5.30"
    id("com.squareup.sqldelight")
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                // KTOR
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")

                // KOTLINX
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

                // SQL DELIGHT
                implementation ("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                // KTOR
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                // SQL DELIGHT
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                // KTOR
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                // SQL DELIGHT
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 31
    }
}

sqldelight {
    database("LocalDB") {
        packageName = "de.simon.covid19.datalayer.database.covid19"
        sourceFolders = listOf("kotlin")
    }
}