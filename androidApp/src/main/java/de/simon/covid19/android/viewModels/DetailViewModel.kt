package de.simon.covid19.android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.simon.covid19.android.viewModels.states.DetailState
import de.simon.covid19.repositories.Covid19Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val countryCode: String,
    private val covid19Repository: Covid19Repository
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }

    private val _viewState: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.LOADING)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val countrySummary = covid19Repository.getCountrySummary(countryCode)
            _viewState.value = _viewState.value.copy(countrySummary = countrySummary)
            val countryDetails = covid19Repository.getCountryDetails(countryCode)

            if (countryDetails.isEmpty) {
                // Error state
                _viewState.value = _viewState.value.copy(
                    isLoading = DetailState.ERROR.isLoading,
                    isEmpty = DetailState.ERROR.isEmpty
                )
            } else {
                _viewState.value = _viewState.value.copy(
                    isLoading = false,
                    isEmpty = false,
                    countryDetails = countryDetails
                )
            }
        }
    }
}