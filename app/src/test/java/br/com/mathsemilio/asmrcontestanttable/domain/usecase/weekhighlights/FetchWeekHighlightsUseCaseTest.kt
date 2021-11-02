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
class FetchWeekHighlightsUseCaseTest {

    companion object {
        private val WEEK_HIGHLIGHTS = WeekHighlightsTestDataProvider.weekHighlights
    }

    @get:Rule
    val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listenerMock: FetchWeekHighlightsUseCase.Listener

    private lateinit var weekHighlightsEndpointTestDouble: WeekHighlightsEndpointTestDouble

    private lateinit var SUT: FetchWeekHighlightsUseCase

    @Before
    fun setUp() {
        weekHighlightsEndpointTestDouble = WeekHighlightsEndpointTestDouble()

        SUT = FetchWeekHighlightsUseCase(weekHighlightsEndpointTestDouble)

        SUT.addListener(listenerMock)
    }

    @Test
    fun fetchWeekHighlights_endpointInvokedSuccessfully() = runBlockingTest {
        SUT.fetchWeekHighlights()

        assertTrue(weekHighlightsEndpointTestDouble.wasInvoked())
    }

    @Test
    fun fetchWeekHighlights_success_listenerNotifiedOfSuccess() = runBlockingTest {
        weekHighlightsEndpointTestDouble.arrangeSuccess()

        SUT.fetchWeekHighlights()

        verify(listenerMock).onWeekHighlightsFetchedSuccessfully(WEEK_HIGHLIGHTS)
    }

    @Test
    fun fetchWeekHighlights_failed_listenerNotifiedOfFailure() = runBlockingTest {
        weekHighlightsEndpointTestDouble.arrangeFailure()

        SUT.fetchWeekHighlights()

        verify(listenerMock).onWeekHighlightsFetchFailed()
    }

    private inner class WeekHighlightsEndpointTestDouble : WeekHighlightsEndpoint(
        weekHighlightsDAO = null
    ) {
        private var isSuccessArranged = false

        private var callCounter = 0

        override suspend fun fetchWeekHighlights(): Result<List<WeekHighlights>> {
            ++callCounter

            return if (isSuccessArranged)
                Result.Completed(data = WEEK_HIGHLIGHTS)
            else
                Result.Failed(errorMessage = null)
        }

        fun wasInvoked(): Boolean {
            return callCounter == 1
        }

        fun arrangeSuccess() {
            isSuccessArranged = true
        }

        fun arrangeFailure() {
            isSuccessArranged = false
        }
    }
}