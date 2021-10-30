package de.simon.covid19.viewModels.actions

sealed class HomeAction {
    class InputChanged(val input: String) : HomeAction()
    object InputDeleted : HomeAction()
}