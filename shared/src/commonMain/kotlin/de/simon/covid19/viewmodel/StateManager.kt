package de.simon.covid19.viewmodel

import de.simon.covid19.datalayer.Repository
import de.simon.covid19.repositories.Covid19Repository
import de.simon.covid19.viewmodel.screens.ScreenType
import de.simon.covid19.viewmodel.screens.ScreenState

class StateManager(repo: Repository) {

    internal val repository by lazy { repo }

    val screenStatesMap : MutableMap<ScreenType, ScreenState> = mutableMapOf()


}