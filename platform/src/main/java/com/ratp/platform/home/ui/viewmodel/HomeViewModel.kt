package com.ratp.platform.home.ui.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratp.business.common.model.Failure
import com.ratp.business.common.model.Success
import com.ratp.business.common.usecase.LocationUseCase
import com.ratp.business.home.usecases.HomeFilterUseCase
import com.ratp.business.home.usecases.HomeUseCase
import com.ratp.platform.home.factory.HomeFactory
import com.ratp.platform.home.model.HomeViewAction
import com.ratp.platform.home.model.HomeViewState
import com.ratp.platform.home.model.OnFilterSwitched
import com.ratp.platform.home.model.OnLoadMore
import com.ratp.platform.home.model.OnStart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUsesCase: HomeUseCase,
    private val locationUseCase: LocationUseCase,
    private val homeFilterUseCase: HomeFilterUseCase,
    private val homeFactory: HomeFactory
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

    private var fetchHomeJob: Job? = null

    init {
        sendAction(OnStart)
    }

    fun sendAction(action: HomeViewAction) {
        when (action) {
            OnStart -> {
                fetchData()
            }
            OnLoadMore -> {}
            OnFilterSwitched -> {
                if (homeFilterUseCase.accessPrmFilterEnabled) {
                    homeFilterUseCase.disableAccessPrmFilter()
                } else {
                    homeFilterUseCase.enableAccessPrmFilter()
                }
                fetchData()
            }
        }
    }

    private fun fetchData() {
        fetchHomeJob?.cancel()
        fetchHomeJob = viewModelScope.launch(Dispatchers.IO) {
            when (val response = homeUsesCase.fetchData()) {
                is Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            homeElements = response.result
                                .map {
                                    homeFactory.generateViewElement(
                                        it,
                                        it.location?.let { location ->
                                            locationUseCase.getDistance(Location("").apply {
                                                latitude = location.first()
                                                longitude = location.last()
                                            })
                                        })
                                },
                            error = null
                        )
                    }
                }

                is Failure -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            homeElements = emptyList(),
                            error = response.error
                        )
                    }
                }
            }
        }
    }
}