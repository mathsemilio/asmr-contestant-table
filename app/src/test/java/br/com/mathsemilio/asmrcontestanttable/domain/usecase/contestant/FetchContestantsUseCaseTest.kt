package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestant

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.testdata.ContestantsTestDataProvider
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
class FetchContestantsUseCaseTest {

    companion object {
        private val CONTESTANTS = ContestantsTestDataProvider.contestants
    }

    @get:Rule
    val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: FetchContestantsUseCase.Listener

    private lateinit var contestantsEndpointTestDouble: ContestantsEndpointTestDouble

    private lateinit var SUT: FetchContestantsUseCase

    @Before
    fun setUp() {
        contestantsEndpointTestDouble = ContestantsEndpointTestDouble()

        SUT = FetchContestantsUseCase(contestantsEndpointTestDouble)

        SUT.addListener(listenerMock)
    }

    @Test
    fun fetchContestants_endpointInvokedSuccessfully() = runBlockingTest {
        SUT.fetchContestants()

        assertTrue(contestantsEndpointTestDouble.wasInvoked())
    }

    @Test
    fun fetchContestants_success_listenersNotifiedOfSuccess() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeSuccess()

        SUT.fetchContestants()

        verify(listenerMock).onContestantsFetchedSuccessfully(CONTESTANTS)
    }

    @Test
    fun fetchContestants_failed_listenersNotifiedOfFailure() = runBlockingTest {
        contestantsEndpointTestDouble.arrangeFailure()

        SUT.fetchContestants()

        verify(listenerMock).onContestantsFetchFailed()
    }

    private inner class ContestantsEndpointTestDouble : ContestantsEndpoint(
        contestantsDAO = null,
        weekHighlightsDAO = null
    ) {
        private var isSuccessArranged = false

        private var callCount = 0

        override suspend fun fetchContestants(): Result<List<ASMRContestant>> {
            ++callCount

            return if (isSuccessArranged)
                Result.Completed(data = CONTESTANTS)
            else
                Result.Failed(errorMessage = "Failed to fetch contestants")
        }

        fun wasInvoked() = callCount >= 1

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }
    }
}