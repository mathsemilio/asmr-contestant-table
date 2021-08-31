package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.view.AddWeekHighlightsView
import br.com.mathsemilio.asmrcontestanttable.util.MainDispatcherCoroutineRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
class AddWeekHighlightsBottomSheetControllerTest {

    companion object {
        const val FIRST_CONTESTANT_NAME = "Sirius Eyes ASMR"
        const val SECOND_CONTESTANT_NAME = "Pelagea ASMR"
    }

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var messagesManagerMock: MessagesManager
    @Mock
    private lateinit var viewMock: AddWeekHighlightsView
    @Mock
    private lateinit var controllerEventListener: WeekHighlightsControllerEventListener

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var eventPublisherTestDouble: EventPublisherTestDouble
    private lateinit var addWeekHighlightsUseCaseTestDouble: AddWeekHighlightsUseCaseTestDouble

    private lateinit var controller: AddWeekHighlightsBottomSheetController

    @Before
    fun setUp() {
        coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        eventPublisherTestDouble = EventPublisherTestDouble()
        addWeekHighlightsUseCaseTestDouble = AddWeekHighlightsUseCaseTestDouble()

        controller = AddWeekHighlightsBottomSheetController(
            messagesManagerMock,
            eventPublisherTestDouble,
            coroutineScope,
            addWeekHighlightsUseCaseTestDouble
        )

        controller.bindView(viewMock)
        controller.registerForControllerEvents(controllerEventListener)
    }

    @Test
    fun onStart_listenersRegistered() {
        controller.onStart()

        verify(viewMock).addListener(controller)
        addWeekHighlightsUseCaseTestDouble.verifyListenerRegistered(controller)
    }

    @Test
    fun onStop_listenersUnregistered() {
        controller.onStop()

        verify(viewMock).removeListener(controller)
        addWeekHighlightsUseCaseTestDouble.verifyListenerUnregistered(controller)
    }

    @Test
    fun onAddButtonClicked_useCaseInvokedWithCorrectParameters() = runBlockingTest {
        controller.onAddButtonClicked(FIRST_CONTESTANT_NAME, SECOND_CONTESTANT_NAME)

        verify(viewMock).changeAddButtonState()
        addWeekHighlightsUseCaseTestDouble.verifyAddWeekHighlightsWasInvokedWith(
            FIRST_CONTESTANT_NAME,
            SECOND_CONTESTANT_NAME
        )
    }

    @Test
    fun onWeekHighlightsAddedSuccessfully_weekHighlightsAddedSuccessfullyEventTriggered() {
        controller.onWeekHighlightsAddedSuccessfully()

        verify(controllerEventListener).onWeekHighlightsModified()
        eventPublisherTestDouble.verifyEventWasPublished(
            WeekHighlightsModifiedEvent.WeekHighlightAdded
        )
    }

    @Test
    fun onAddWeekHighlightsFailed_errorStateShown() {
        controller.onAddWeekHighlightsFailed()

        verify(viewMock).revertAddButtonState()
        verify(messagesManagerMock).showUnexpectedErrorOccurredMessage()
    }

    private inner class EventPublisherTestDouble : EventPublisher(null) {

        private lateinit var eventPublished: Any

        override fun publish(event: Any): Unit? {
            eventPublished = event
            return null
        }

        fun verifyEventWasPublished(event: WeekHighlightsModifiedEvent.WeekHighlightAdded) {
            if (eventPublished is WeekHighlightsModifiedEvent.WeekHighlightAdded) {
                if (event == eventPublished)
                    return
                else
                    throw RuntimeException("Event was not published")
            } else {
                throw RuntimeException("Incorrect event was published")
            }
        }
    }

    private inner class AddWeekHighlightsUseCaseTestDouble : AddWeekHighlightsUseCase(null) {

        private lateinit var firstContestantName: String
        private lateinit var secondContestantName: String

        private var callCount = 0

        override suspend fun insertWeekHighlights(
            firstContestantName: String,
            secondContestantName: String
        ) {
            ++callCount
            this.firstContestantName = firstContestantName
            this.secondContestantName = secondContestantName
        }

        fun verifyListenerRegistered(candidate: Listener) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    return
            }

            throw RuntimeException("Listener not registered")
        }

        fun verifyListenerUnregistered(candidate: Listener) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    throw RuntimeException("Listener not unregistered")
            }
        }

        fun verifyAddWeekHighlightsWasInvokedWith(
            firstContestantName: String,
            secondContestantName: String
        ) {
            if (callCount == 1 && areParametersCorrect(firstContestantName, secondContestantName))
                return
            else
                throw RuntimeException("Use case invoked with incorrect parameters")
        }

        private fun areParametersCorrect(
            firstContestantName: String,
            secondContestantName: String
        ): Boolean {
            return this.firstContestantName == firstContestantName
                    && this.secondContestantName == secondContestantName
        }
    }
}