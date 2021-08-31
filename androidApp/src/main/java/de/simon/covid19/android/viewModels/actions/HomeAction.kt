package de.simon.covid19.android.viewModels.actions

sealed class HomeAction {
    class InputChanged(val input: String) : HomeAction()
    object InputDelete : HomeAction()
}