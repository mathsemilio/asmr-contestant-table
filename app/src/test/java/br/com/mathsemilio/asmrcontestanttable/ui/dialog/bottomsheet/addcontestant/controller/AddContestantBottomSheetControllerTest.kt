package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view.AddContestantView
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
class AddContestantBottomSheetControllerTest {

    companion object {
        private const val CONTESTANT_NAME = "Sirius Eyes ASMR"
    }

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var messagesManagerMock: MessagesManager
    @Mock
    private lateinit var viewMock: AddContestantView
    @Mock
    private lateinit var controllerEventListenerMock: ContestantsControllerEventListener

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var addContestantUseCaseTestDouble: AddContestantsUseCaseTestDouble
    private lateinit var eventPublisherTestDouble: EventPublisherTestDouble

    private lateinit var controller: AddContestantBottomSheetController

    @Before
    fun setUp() {
        coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        addContestantUseCaseTestDouble = AddContestantsUseCaseTestDouble()
        eventPublisherTestDouble = EventPublisherTestDouble()

        controller = AddContestantBottomSheetController(
            messagesManagerMock,
            coroutineScope,
            eventPublisherTestDouble,
            addContestantUseCaseTestDouble
        )

        controller.bindView(viewMock)
        controller.registerForControllerEvents(controllerEventListenerMock)
    }

    @Test
    fun onStart_listenersRegistered() {
        controller.onStart()

        verify(viewMock).addListener(controller)
        addContestantUseCaseTestDouble.verifyListenerRegistered(controller)
    }

    @Test
    fun onStop_listenersUnregistered() {
        controller.onStop()

        verify(viewMock).removeListener(controller)
        addContestantUseCaseTestDouble.verifyListenerUnregistered(controller)
    }

    @Test
    fun onAddButtonClicked_useCaseInvokedWithCorrectParameters() = runBlockingTest {
        controller.onAddButtonClicked(CONTESTANT_NAME)

        verify(viewMock).changeAddButtonState()
        addContestantUseCaseTestDouble.verifyTestDoubleWasCalledWith(CONTESTANT_NAME)
    }

    @Test
    fun onContestantAddedSuccessfully_contestantAddedEventPublished() {
        controller.onContestantAddedSuccessfully()

        verify(controllerEventListenerMock).onContestantsModified()
        eventPublisherTestDouble.verifyEventWasPosted(ContestantsModifiedEvent.ContestantAdded)
    }

    @Test
    fun onAddContestantFailed_errorStateShown() {
        controller.onAddContestantFailed()

        verify(viewMock).revertAddButtonState()
        verify(messagesManagerMock).showUnexpectedErrorOccurredMessage()
    }

    inner class EventPublisherTestDouble : EventPublisher(null) {

        private lateinit var publishedEvent: Any

        override fun publish(event: Any): Unit? {
            publishedEvent = event
            return null
        }

        fun verifyEventWasPosted(event: ContestantsModifiedEvent.ContestantAdded) {
            if (publishedEvent is ContestantsModifiedEvent.ContestantAdded) {
                if (publishedEvent == event)
                    return
                else
                    throw RuntimeException("Event was not posted")
            } else {
                throw RuntimeException("Event with incorrect type was posted")
            }
        }
    }

    inner class AddContestantsUseCaseTestDouble : AddContestantUseCase(null) {

        private var callCount = 0

        private lateinit var contestantName: String

        override suspend fun addContestant(contestantName: String) {
            ++callCount
            this.contestantName = contestantName
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

        fun verifyTestDoubleWasCalledWith(contestantName: String) {
            if (callCount == 1 && isContestantNameEqual(contestantName))
                return
            else
                throw RuntimeException("Use case not invoked with correct parameters")
        }

        private fun isContestantNameEqual(contestantName: String): Boolean {
            return contestantName == this.contestantName
        }
    }
}