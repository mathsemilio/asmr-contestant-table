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
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.util.MainDispatcherCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteContestantsUseCaseTest {

    @get:Rule
    val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: DeleteContestantsUseCase.Listener

    private lateinit var contestantsEndpointTestDouble: ContestantsEndpointTestDouble

    private lateinit var SUT: DeleteContestantsUseCase

    @Before
    fun setUp() {
        contestantsEndpointTestDouble = ContestantsEndpointTestDouble()

        SUT = DeleteContestantsUseCase(contestantsEndpointTestDouble)

        SUT.addListener(listenerMock)
    }

    @Test
    fun deleteAllContestants_endpointInvokedSuccessfully() = runBlockingTest {
        SUT.deleteAllContestants()

        assertTrue(contestantsEndpointTestDouble.wasEndpointInvoked())
    }

    @Test
    fun deleteAllContestants_success_listenersNotifiedOfSuccess() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeSuccess()

        SUT.deleteAllContestants()

        verify(listenerMock).onAllContestantsDeletedSuccessfully()
    }

    @Test
    fun deleteAllContestants_failed_listenersNotifiedOfFailure() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeFailure()

        SUT.deleteAllContestants()

        verify(listenerMock).onDeleteAllContestantsFailed()
    }

    private inner class ContestantsEndpointTestDouble : ContestantsEndpoint(
        contestantsDAO = null,
        weekHighlightsDAO = null
    ) {
        private var callCount = 0

        private var isSuccessArranged = false

        override suspend fun deleteAllContestants(): Result<Nothing> {
            ++callCount

            return if (isSuccessArranged)
                Result.Completed(data = null)
            else
                Result.Failed(errorMessage = "Failed to delete all contestants")
        }

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }

        fun wasEndpointInvoked() = callCount >= 1
    }
}