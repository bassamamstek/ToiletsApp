package com.ratp.business.home

import com.ratp.business.home.repository.HomeFilterRepository
import com.ratp.business.home.repository.HomeFilterRepositoryImpl
import com.ratp.business.home.usecases.HomeFilterUseCaseImpl
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HomeFilterUseCaseTest {

    private val homeFilterRepository: HomeFilterRepository = HomeFilterRepositoryImpl()

    private val homeFilterUsesCase =
        HomeFilterUseCaseImpl(homeFilterRepository)

    @Test
    fun WHEN_enableAccessPrmFilter_called_THEN_homeFilterRepository_is_updated() =
        runTest {

            // when
            homeFilterUsesCase.enableAccessPrmFilter()

            // then
            TestCase.assertTrue(homeFilterRepository.accessPrm)
            TestCase.assertTrue(homeFilterUsesCase.accessPrmFilterEnabled)

        }

    @Test
    fun WHEN_disableAccessPrmFilter_called_THEN_homeFilterRepository_is_updated() =
        runTest {

            // when
            homeFilterUsesCase.disableAccessPrmFilter()

            // then
            TestCase.assertFalse(homeFilterRepository.accessPrm)
            TestCase.assertFalse(homeFilterUsesCase.accessPrmFilterEnabled)

        }
}