package de.simon.covid19.viewmodel

import de.simon.covid19.datalayer.Repository
import de.simon.covid19.repositories.Covid19Repository

class BaseViewModel(repository: Repository) {

    companion object Factory {

    }

    private val stateManager by lazy { StateManager(repository) }
}