package com.ratp.data.home.repository

import com.ratp.business.common.model.AppError
import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositorySuccess
import com.ratp.data.ModelTestProvider
import com.ratp.data.common.RepositoryFailureImpl
import com.ratp.data.common.RepositorySuccessImpl
import com.ratp.data.home.datasource.HomeLocalDataSource
import com.ratp.data.home.datasource.HomeRemoteDataSource
import com.ratp.data.home.model.toBusinessModel
import com.ratp.data.remoteconfig.RemoteConfigRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class HomeRepositoryTest {

    private val homeRemoteDataSource: HomeRemoteDataSource = mockk()
    private val homeLocalDataSource: HomeLocalDataSource = mockk()
    private val remoteConfigRepository: RemoteConfigRepository = mockk()

    private val homeRepository =
        HomeRepositoryImpl(homeRemoteDataSource, homeLocalDataSource, remoteConfigRepository)

    private val provider = ModelTestProvider()

    @Test
    fun GIVEN_localData_WHEN_InitialFetchData_THEN_return_correct_result() = runTest {
        // given
        val localData = provider.get(5, 0)
        every { homeLocalDataSource.data } returns localData.toBusinessModel().toMutableList()

        // when
        val repositoryResponse = homeRepository.initialFetchlData()

        // then
        val successResponse = repositoryResponse as RepositorySuccess
        assertEquals(localData.toBusinessModel(), successResponse.response)
        coVerify(exactly = 0) { homeRemoteDataSource.fetch(any(), 0) }
        coVerify(exactly = 0) { homeLocalDataSource.cache(any()) }
    }

    @Test
    fun GIVEN_noLocalData_and_success_WHEN_InitialFetchData_THEN_return_correct_result() = runTest {
        // given
        val url = "www.test.fr"
        every { remoteConfigRepository.dataSetUrl } returns url
        val remoteResponse = provider.get(5, 0)
        every { homeLocalDataSource.data } returns null
        every { homeLocalDataSource.cache(any()) } returns Unit
        coEvery { homeRemoteDataSource.fetch(any(), 0) } coAnswers {
            RepositorySuccessImpl(
                remoteResponse
            )
        }

        // when
        val repositoryResponse = homeRepository.initialFetchlData()

        // then
        val successResponse = repositoryResponse as RepositorySuccess
        assertEquals(remoteResponse.toBusinessModel(), successResponse.response)
        coVerify(exactly = 1) { homeRemoteDataSource.fetch(url, 0) }
        coVerify(exactly = 1) { homeLocalDataSource.cache(successResponse.response) }
    }

    @Test
    fun GIVEN_noLocalData_and_failure_WHEN_InitialFetchData_THEN_return_correct_result() = runTest {
        // given
        val url = "www.test.fr"
        every { remoteConfigRepository.dataSetUrl } returns url
        every { homeLocalDataSource.data } returns null
        every { homeLocalDataSource.cache(any()) } returns Unit
        coEvery {
            homeRemoteDataSource.fetch(
                any(),
                0
            )
        } coAnswers { RepositoryFailureImpl(AppError.EMPTY) }

        // when
        val repositoryResponse = homeRepository.initialFetchlData()

        // then
        TestCase.assertTrue(repositoryResponse is RepositoryFailure)
        assertEquals(AppError.EMPTY, (repositoryResponse as RepositoryFailureImpl).error)
        coVerify(exactly = 1) { homeRemoteDataSource.fetch(url, 0) }
    }
}