package br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint
import br.com.mathsemilio.asmrcontestanttable.testdata.WeekHighlightsTestDataProvider
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
class AddWeekHighlightsUseCaseTest {

    companion object {
        private val FIRST_CONTESTANT_NAME =
            WeekHighlightsTestDataProvider.weekHighlights[0].firstContestantName
        private val SECOND_CONTESTANT_NAME =
            WeekHighlightsTestDataProvider.weekHighlights[0].secondContestantName

        private val WEEK_HIGHLIGHTS = WeekHighlightsTestDataProvider.weekHighlights[0]

        private const val CONTEST_FIRST_WEEK = 1
    }

    @get:Rule
    val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: AddWeekHighlightsUseCase.Listener

    private lateinit var weekHighlightsEndpointTestDouble: WeekHighlightsEndpointTestDouble

    private lateinit var SUT: AddWeekHighlightsUseCase

    @Before
    fun setUp() {
        weekHighlightsEndpointTestDouble = WeekHighlightsEndpointTestDouble()

        SUT = AddWeekHighlightsUseCase(weekHighlightsEndpointTestDouble)

        SUT.addListener(listenerMock)
    }

    @Test
    fun insertWeekHighlights_endpointInvokedSuccessfully() = runBlockingTest {
        SUT.insertWeekHighlights(FIRST_CONTESTANT_NAME, SECOND_CONTESTANT_NAME)

        assertTrue(weekHighlightsEndpointTestDouble.wasInvokedWith(WEEK_HIGHLIGHTS))
    }

    @Test
    fun insertWeekHighlights_success_listenerNotifiedOfSuccess() = runBlockingTest {
        weekHighlightsEndpointTestDouble.arrangeSuccess()

        SUT.insertWeekHighlights(FIRST_CONTESTANT_NAME, SECOND_CONTESTANT_NAME)

        verify(listenerMock).onWeekHighlightsAddedSuccessfully()
    }

    @Test
    fun insertWeekHighlights_failure_listenerNotifiedOfFailure() = runBlockingTest {
        weekHighlightsEndpointTestDouble.arrangeFailure()

        SUT.insertWeekHighlights(FIRST_CONTESTANT_NAME, SECOND_CONTESTANT_NAME)

        verify(listenerMock).onAddWeekHighlightsFailed()
    }

    private inner class WeekHighlightsEndpointTestDouble : WeekHighlightsEndpoint(
        weekHighlightsDAO = null
    ) {
        private lateinit var weekHighlights: WeekHighlights

        private var insertWeekHighlightsCallCounter = 0
        private var getWeekNumberCallCounter = 0

        private var isSuccessArranged = false

        override suspend fun insertWeekHighlights(weekHighlights: WeekHighlights): Result<Nothing> {
            this.weekHighlights = weekHighlights
            ++insertWeekHighlightsCallCounter

            return if (isSuccessArranged)
                Result.Completed(data = null)
            else
                Result.Failed(errorMessage = null)
        }

        override suspend fun getWeekNumber(): Result<Int> {
            ++getWeekNumberCallCounter

            return Result.Completed(data = CONTEST_FIRST_WEEK)
        }

        fun wasInvokedWith(candidate: WeekHighlights): Boolean {
            return wasEndpointFunctionsInvoked() && candidate == weekHighlights
        }

        private fun wasEndpointFunctionsInvoked(): Boolean {
            return insertWeekHighlightsCallCounter == 1 && getWeekNumberCallCounter == 1
        }

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }
    }
}