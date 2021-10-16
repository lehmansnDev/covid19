package de.simon.covid19.viewmodel.screens

enum class ScreenType(
    val asString: String,
    val navigationLevel : Int = 1,
    val stackableInstances : Boolean = false,
) {
    Home("Home", 1, true),
    Detail("Detail", 2),
}