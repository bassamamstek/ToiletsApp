package com.ratp.business.home

import com.ratp.business.ModelTestProvider
import com.ratp.business.RepositoryFailureTestModel
import com.ratp.business.RepositorySuccessTestModel
import com.ratp.business.common.model.AppError
import com.ratp.business.common.model.Failure
import com.ratp.business.common.model.Success
import com.ratp.business.home.model.ToiletBusinessModel
import com.ratp.business.home.repository.HomeFilterRepository
import com.ratp.business.home.repository.HomeRepository
import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositoryResponse
import com.ratp.business.home.usecases.HomeUsesCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class HomeUsesCaseTest {

    private val homeRepository: HomeRepository = mockk()
    private val homeFilterRepository: HomeFilterRepository = mockk()

    private val homeUsesCase =
        HomeUsesCaseImpl(homeRepository, homeFilterRepository)
    private val modelTestProvider = ModelTestProvider()

    @Test
    fun GIVEN_success_repository_response_WHEN_fetchHomeData_And_filterAccessPrm_is_false_THEN_return_correct_result() =
        runTest {
            // given
            val repositoryResponse: RepositoryResponse<List<ToiletBusinessModel>> =
                RepositorySuccessTestModel(
                    modelTestProvider.get(20)
                )
            coEvery { homeRepository.initialFetchlData() } coAnswers { repositoryResponse }
            coEvery { homeFilterRepository.accessPrm } coAnswers { false }

            // when
            val result = homeUsesCase.fetchData()

            // then
            val successResult = result as Success
            assertEquals(21, successResult.result.size)
            assertEquals("0", successResult.result[0].id)
            assertEquals("1", successResult.result[1].id)
            assertEquals("2", successResult.result[2].id)
        }

    @Test
    fun GIVEN_success_repository_response_WHEN_fetchHomeData_And_filterAccessPrm_is_true_THEN_return_correct_result() =
        runTest {
            // given
            val repositoryResponse: RepositoryResponse<List<ToiletBusinessModel>> =
                RepositorySuccessTestModel(
                    modelTestProvider.get(20)
                )
            coEvery { homeRepository.initialFetchlData() } coAnswers { repositoryResponse }
            coEvery { homeFilterRepository.accessPrm } coAnswers { true }

            // when
            val result = homeUsesCase.fetchData()

            // then
            val successResult = result as Success
            assertEquals(11, successResult.result.size)

        }

    @Test
    fun GIVEN_failed_repository_response_WHEN_fetchHomeData_THEN_return_correct_result() =
        runTest {
            // given
            val repositoryResponse: RepositoryFailure<List<ToiletBusinessModel>> =
                RepositoryFailureTestModel()
            coEvery { homeRepository.initialFetchlData() } coAnswers { repositoryResponse }

            // when
            val result = homeUsesCase.fetchData()

            // then
            val failureResult = result as Failure
            assertEquals(AppError.UNKNOWN, failureResult.error)
        }
}