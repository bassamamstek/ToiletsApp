package com.ratp.data.home.datasource

import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositorySuccess
import com.ratp.data.ModelTestProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

internal class HomeRemoteDataSourceTest {

    private val homeDataApi: HomeDataApi = mockk()

    private val homeRemoteDataSource = HomeRemoteDataSource(homeDataApi)

    private val modelTestProvider = ModelTestProvider()

    @Test
    fun GIVEN_success_data_result_WHEN_fetch_THEN_return_correct_result() = runTest {
        // given
        val url = "www.exempleurl.fr"
        val homeCryptoList = modelTestProvider.get(5, 0)
        coEvery { homeDataApi.fetch(url, 0) } returns Response.success(homeCryptoList)

        // when
        val response = homeRemoteDataSource.fetch(url, 0)

        // then
        val successResponse = response as RepositorySuccess
        TestCase.assertEquals(homeCryptoList, successResponse.response)
    }

    @Test
    fun GIVEN_success_and_null_data_WHEN_fetch_THEN_return_correct_result() = runTest {
        // given
        val url = "www.exempleurl.fr"
        coEvery { homeDataApi.fetch(url, 0) } returns Response.success(null)

        // when
        val response = homeRemoteDataSource.fetch(url, 0)

        // then
        TestCase.assertTrue(response is RepositoryFailure)
    }

    @Test
    fun GIVEN_failure_result_WHEN_fetch_THEN_return_correct_result() = runTest {
        // given
        val url = "www.exempleurl.fr"
        coEvery { homeDataApi.fetch(url, 0) } returns Response.error(400, mockk(relaxed = true))

        // when
        val response = homeRemoteDataSource.fetch(url, 0)

        // then
        TestCase.assertTrue(response is RepositoryFailure)
    }

    @Test
    fun GIVEN_http_exception_result_WHEN_fetch_THEN_return_correct_result() = runTest {
        // given
        val url = "www.exempleurl.fr"
        coEvery { homeDataApi.fetch(url, 0) } coAnswers { throw HttpException(mockk()) }

        // when
        val response = homeRemoteDataSource.fetch(url, 0)

        // then
        TestCase.assertTrue(response is RepositoryFailure)
    }
}