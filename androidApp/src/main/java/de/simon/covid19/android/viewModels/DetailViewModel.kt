package de.simon.covid19.android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.simon.covid19.repository.Repository
import de.simon.covid19.viewModels.states.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val countryCode: String,
    private val repository: Repository
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.simpleName
    }

    private val _viewState: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.LOADING)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val countrySummary = repository.getCountrySummary(countryCode)
            _viewState.value = _viewState.value.copy(countrySummary = countrySummary)

            // TODO: Implement monthly statistics
//            val countryDetails = repository.getCountryDetails(countryCode)
//
//            if (countryDetails.isEmpty) {
//                // Error state
//                _viewState.value = _viewState.value.copy(
//                    loading = DetailState.FAILED.loading,
//                    failed = DetailState.FAILED.failed
//                )
//            } else {
//                _viewState.value = _viewState.value.copy(
//                    loading = false,
//                    failed = false,
//                    countryDetails = countryDetails
//                )
//            }
        }
    }
}