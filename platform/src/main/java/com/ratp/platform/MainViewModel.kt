package com.ratp.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratp.business.common.repository.LocationRepository
import com.ratp.platform.location.PlayServicesAvailabilityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val availabilityChecker: PlayServicesAvailabilityChecker,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState: StateFlow<MainViewState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (availabilityChecker.isGooglePlayServicesAvailable()) {
                _uiState.update {
                    it.copy(
                        state = UiState.PlayServicesAvailable
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        state = UiState.PlayServicesUnavailable
                    )
                }
            }
        }
    }


    fun getCurrentLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation()
            _uiState.update {
                it.copy(
                    state = UiState.LocationCalled
                )
            }
        }
    }
}

enum class UiState {
    Initializing, PlayServicesUnavailable, PlayServicesAvailable, LocationCalled
}

data class MainViewState(
    var state: UiState = UiState.Initializing,
)
