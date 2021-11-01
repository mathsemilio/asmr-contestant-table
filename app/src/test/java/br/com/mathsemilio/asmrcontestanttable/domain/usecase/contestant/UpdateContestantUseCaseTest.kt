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
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.testdata.ContestantsTestDataProvider
import br.com.mathsemilio.asmrcontestanttable.util.MainDispatcherCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UpdateContestantUseCaseTest {

    companion object {
        private val CONTESTANT_1 =
            ContestantsTestDataProvider.contestants[0]
        private val CONTESTANT_2 =
            ContestantsTestDataProvider.contestants[1]
        private val CONTESTANT_3 =
            ContestantsTestDataProvider.contestants[2]

        private val UPDATED_CONTESTANT_1 =
            ContestantsTestDataProvider.updatedContestants[0]
        private val UPDATED_CONTESTANT_2 =
            ContestantsTestDataProvider.updatedContestants[1]
        private val UPDATED_CONTESTANT_3 =
            ContestantsTestDataProvider.updatedContestants[2]
    }

    @get:Rule
    val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: UpdateContestantUseCase.Listener

    private lateinit var contestantsEndpointTestDouble: ContestantsEndpointTestDouble

    private lateinit var SUT: UpdateContestantUseCase

    @Before
    fun setUp() {
        contestantsEndpointTestDouble = ContestantsEndpointTestDouble()

        SUT = UpdateContestantUseCase(contestantsEndpointTestDouble)

        SUT.addListener(listenerMock)
    }

    @Test
    fun updateContestantTimesSlept_endpointInvokedWithCorrectContestant() = runBlockingTest {
        SUT.updateContestantTimesSlept(CONTESTANT_1)

        assertTrue(contestantsEndpointTestDouble.wasInvokedWith(UPDATED_CONTESTANT_1))
    }

    @Test
    fun updateContestantTimesSlept_success_listenerNotifiedOfSuccess() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeSuccess()

        SUT.updateContestantTimesSlept(CONTESTANT_1)

        verify(listenerMock).onContestantUpdatedSuccessfully()
    }

    @Test
    fun updateContestantTimesSlept_failed_listenerNotifiedOfFailure()  = runBlockingTest {
        contestantsEndpointTestDouble.arrangeFailure()

        SUT.updateContestantTimesSlept(CONTESTANT_1)

        verify(listenerMock).onUpdateContestantFailed()
    }

    @Test
    fun updateContestantTimesFeltTired_endpointInvokedWithCorrectContestant() = runBlockingTest {
        SUT.updateContestantTimesFeltTired(CONTESTANT_2)

        assertTrue(contestantsEndpointTestDouble.wasInvokedWith(UPDATED_CONTESTANT_2))
    }

    @Test
    fun updateContestantTimesFeltTired_success_listenerNotifiedOfSuccess() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeSuccess()

        SUT.updateContestantTimesFeltTired(CONTESTANT_2)

        verify(listenerMock).onContestantUpdatedSuccessfully()
    }

    @Test
    fun updateContestantTimesFeltTired_failed_listenerNotifiedOfFailure() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeFailure()

        SUT.updateContestantTimesFeltTired(CONTESTANT_2)

        verify(listenerMock).onUpdateContestantFailed()
    }

    @Test
    fun updateContestantTimesDidNotSlept_endpointInvokedWithCorrectContestant() = runBlockingTest {
        SUT.updateContestantTimesDidNotSlept(CONTESTANT_3)

        assertTrue(contestantsEndpointTestDouble.wasInvokedWith(UPDATED_CONTESTANT_3))
    }

    @Test
    fun updateContestantTimesDidNotSlept_success_listenersNotifiedOfSuccess() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeSuccess()

        SUT.updateContestantTimesDidNotSlept(CONTESTANT_3)

        verify(listenerMock).onContestantUpdatedSuccessfully()
    }

    @Test
    fun updateContestantTimesDidNotSlept_failed_listenersNotifiedOfFailure() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeFailure()

        SUT.updateContestantTimesDidNotSlept(CONTESTANT_3)

        verify(listenerMock).onUpdateContestantFailed()
    }

    private inner class ContestantsEndpointTestDouble : ContestantsEndpoint(null, null) {

        private lateinit var contestant: ASMRContestant

        private var isSuccessArranged = false

        private var callCounter = 0

        override suspend fun updateContestant(contestant: ASMRContestant): Result<Nothing> {
            this.contestant = contestant
            ++callCounter

            return if (isSuccessArranged)
                Result.Completed(data = null)
            else
                Result.Failed(errorMessage = null)
        }

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }

        fun wasInvokedWith(candidate: ASMRContestant): Boolean {
            return callCounter == 1 && candidate == contestant
        }
    }
}