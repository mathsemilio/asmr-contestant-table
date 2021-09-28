/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestant

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.testdata.ContestantsTestDataProvider
import br.com.mathsemilio.asmrcontestanttable.util.MainDispatcherCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddContestantUseCaseTest {

    companion object {
        private const val CONTESTANT_NAME = "Sirius Eyes ASMR"
        private val CONTESTANT = ContestantsTestDataProvider.contestants.first()
    }

    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: AddContestantUseCase.Listener

    private lateinit var endpointTestDouble: ContestantsEndpointTestDouble

    private lateinit var useCase: AddContestantUseCase

    @Before
    fun setUp() {
        endpointTestDouble = ContestantsEndpointTestDouble()

        useCase = AddContestantUseCase(endpointTestDouble)
        useCase.addListener(listenerMock)
    }

    @Test
    fun addContestant_endpointInvokedWithCorrectParameters() = runBlockingTest {
        useCase.addContestant(CONTESTANT_NAME)

        endpointTestDouble.verifyInteraction(CONTESTANT)
    }

    @Test
    fun addContestant_success_listenerNotifiedOfSuccessEvent() = runBlockingTest {
        endpointTestDouble.arrangeSuccess()

        useCase.addContestant(CONTESTANT_NAME)

        verify(listenerMock).onContestantAddedSuccessfully()
    }

    @Test
    fun addContestant_error_failedResultReturnedByEndpoint() = runBlockingTest {
        endpointTestDouble.arrangeFailure()

        useCase.addContestant(CONTESTANT_NAME)

        verify(listenerMock).onAddContestantFailed()
    }

    private inner class ContestantsEndpointTestDouble : ContestantsEndpoint(
        contestantsDAO = null,
        weekHighlightsDAO = null
    ) {
        private lateinit var contestant: ASMRContestant

        private var isSuccessArranged = false

        private var callCount = 0

        override suspend fun insertContestant(contestant: ASMRContestant): Result<Nothing> {
            ++callCount
            this.contestant = contestant

            return if (isSuccessArranged)
                Result.Completed(data = null)
            else
                Result.Failed(errorMessage = null)
        }

        fun verifyInteraction(contestant: ASMRContestant) {
            if (callCount == 1 && this.contestant == contestant)
                return
            else
                throw RuntimeException("No interaction with the endpoint")
        }

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }
    }
}