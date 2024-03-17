package com.ratp.platform.home

import com.ratp.business.common.model.AppError
import com.ratp.business.common.model.Failure
import com.ratp.business.common.model.Success
import com.ratp.business.common.usecase.LocationUseCase
import com.ratp.business.home.usecases.HomeFilterUseCase
import com.ratp.business.home.usecases.HomeUseCase
import com.ratp.platform.home.factory.HomeFactory
import com.ratp.platform.home.model.OnFilterSwitched
import com.ratp.platform.home.model.OnStart
import com.ratp.platform.home.ui.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HomeViewModelTest {
    private val homeUsesCase: HomeUseCase = mockk(relaxed = true)
    private val homeFilterUsesCase: HomeFilterUseCase = mockk(relaxed = true)
    private val locationUseCase: LocationUseCase = mockk(relaxed = true)

    private val homeFactory: HomeFactory = HomeFactory()

    private val homeViewModel: HomeViewModel = HomeViewModel(
        homeUsesCase,
        locationUseCase,
        homeFilterUsesCase,
        homeFactory
    )

    private val modelTestProvider = ModelTestProvider()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GIVEN_OnStart_WHEN_sendAction_THEN_state_set_correctly() = runTest {
        // given
        val viewElementsSize = 3
        val action = OnStart
        coEvery { homeUsesCase.fetchData() } coAnswers {
            Success(
                modelTestProvider.get(
                    viewElementsSize
                )
            )
        }

        // when
        homeViewModel.sendAction(action)

        // then
        advanceUntilIdle()
        coVerify { homeUsesCase.fetchData() }
        val newState = homeViewModel.uiState.value
        TestCase.assertEquals(viewElementsSize, newState.homeElements.size)
        TestCase.assertNull(newState.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GIVEN_OnStart_and_failure_WHEN_sendAction_THEN_state_set_correctly() = runTest {
        // given
        val networkError = AppError.NETWORK_ERROR
        val action = OnStart
        coEvery { homeUsesCase.fetchData() } coAnswers { Failure(networkError) }

        // when
        homeViewModel.sendAction(action)

        // then
        advanceUntilIdle()
        coVerify { homeUsesCase.fetchData() }
        val newState = homeViewModel.uiState.value
        TestCase.assertEquals(0, newState.homeElements.size)
        TestCase.assertNotNull(newState.error)
        assertEquals(networkError, newState.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GIVEN_OnFilterSwitched_WHEN_sendAction_and_filter_is_disabled_THEN_filter_usecase_called() =
        runTest {
            // given
            val viewElementsSize = 3
            val action = OnFilterSwitched
            coEvery { homeUsesCase.fetchData() } coAnswers {
                Success(
                    modelTestProvider.get(
                        viewElementsSize
                    )
                )
            }

            // when
            homeViewModel.sendAction(action)

            // then
            advanceUntilIdle()
            coVerify { homeFilterUsesCase.enableAccessPrmFilter() }
            coVerify { homeUsesCase.fetchData() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GIVEN_OnFilterSwitched_WHEN_sendAction_and_filter_is_enabled_THEN_filter_usecase_called() =
        runTest {
            // given
            val viewElementsSize = 3
            val action = OnFilterSwitched
            coEvery { homeUsesCase.fetchData() } coAnswers {
                Success(
                    modelTestProvider.get(
                        viewElementsSize
                    )
                )
            }
            coEvery { homeFilterUsesCase.accessPrmFilterEnabled } returns true

            // when
            homeViewModel.sendAction(action)

            // then
            advanceUntilIdle()
            coVerify { homeFilterUsesCase.disableAccessPrmFilter() }
            coVerify { homeUsesCase.fetchData() }
        }

}