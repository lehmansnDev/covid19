package de.simon.covid19.android.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.simon.covid19.android.viewModels.actions.HomeAction
import de.simon.covid19.android.viewModels.states.HomeState
import de.simon.covid19.models.CountrySummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(covid19Repository: Covid19Repository) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }

    private val _viewState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.LOADING)
    val viewState = _viewState.asStateFlow()

    private val allCountries = mutableListOf<CountrySummary>()

    init {
        viewModelScope.launch {
            val covid19Summary = covid19Repository.getTodaySummary()
            if (covid19Summary.isEmpty) {
                _viewState.value = HomeState.ERROR
            } else {
                allCountries.addAll(covid19Summary.countries)
                _viewState.value =
                    HomeState(
                        isLoading = false,
                        isEmpty = false,
                        global = covid19Summary.global,
                        filteredCountries = allCountries,
                        input = ""
                    )
            }
        }
    }

    fun onAction(action: HomeAction) {
        Log.d(TAG, "onAction: ${action::class.simpleName}")
        when (action) {
            is HomeAction.InputChanged -> updateInput(action.input)
            is HomeAction.InputDelete -> updateInput("")
        }
    }

    private fun updateInput(input: String) {
        _viewState.value = _viewState.value.copy(
            input = input,
            filteredCountries = allCountries.filter {
                it.country.lowercase(Locale.ROOT).contains(input.lowercase(Locale.ROOT))
            })
    }
}