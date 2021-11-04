package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.testdata.ContestantsTestDataProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.view.ContestantDetailsView
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
class ContestantsDetailsBottomSheetControllerTest {

    companion object {
        private val CONTESTANT = ContestantsTestDataProvider.contestants[0]
    }

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var messagesManagerMock: MessagesManager
    @Mock
    private lateinit var viewMock: ContestantDetailsView
    @Mock
    private lateinit var eventDelegateMock: ContestantDetailsControllerEventDelegate

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var eventPublisherTestDouble: EventPublisherTestDouble
    private lateinit var updateContestantUseCaseTestDouble: UpdateContestantUseCaseTestDouble

    private lateinit var controller: ContestantsDetailsBottomSheetController

    @Before
    fun setUp() {
        coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        eventPublisherTestDouble = EventPublisherTestDouble()
        updateContestantUseCaseTestDouble = UpdateContestantUseCaseTestDouble()

        controller = ContestantsDetailsBottomSheetController(
            messagesManagerMock,
            eventPublisherTestDouble,
            coroutineScope,
            updateContestantUseCaseTestDouble
        )

        controller.bindView(viewMock)
        controller.bindContestant(CONTESTANT)
        controller.addEventDelegate(eventDelegateMock)
    }

    @Test
    fun onStart_listenersRegistered() {
        controller.onStart()

        verify(viewMock).addListener(controller)
        updateContestantUseCaseTestDouble.verifyListenerRegistered(controller)
    }

    @Test
    fun onStart_contestantBoundToView() {
        controller.onStart()

        verify(viewMock).bindContestant(CONTESTANT)
    }

    @Test
    fun onStop_listenersUnregistered() {
        controller.onStop()

        verify(viewMock).removeListener(controller)
        updateContestantUseCaseTestDouble.verifyListenerUnregistered(controller)
    }

    @Test
    fun onIncrementTimesSleptButtonClicked_useCaseInvokedWithCorrectParameters() = runBlockingTest {
        controller.onIncrementTimesSleptButtonClicked()

        updateContestantUseCaseTestDouble.verifyUseCaseWasInvokedWith(CONTESTANT)
    }

    @Test
    fun onIncrementTimesDidNotSleptButtonClicked_useCaseInvokedWithCorrectParameters() = runBlockingTest {
        controller.onIncrementTimesDidNotSleptButtonClicked()

        updateContestantUseCaseTestDouble.verifyUseCaseWasInvokedWith(CONTESTANT)
    }

    @Test
    fun onIncrementTimesFeltTiredButtonClicked_useCaseInvokedWithCorrectParameters() = runBlockingTest {
        controller.onIncrementTimesFeltTiredButtonClicked()

        updateContestantUseCaseTestDouble.verifyUseCaseWasInvokedWith(CONTESTANT)
    }

    @Test
    fun onContestantUpdatedSuccessfully_contestantModifiedEventWasPublished() {
        controller.onContestantUpdatedSuccessfully()

        verify(eventDelegateMock).onDismissBottomSheetRequested()
        eventPublisherTestDouble.verifyEventWasPublished(ContestantsModifiedEvent.ContestantModified)
    }

    @Test
    fun onUpdateContestantFailed_errorMessageShown() {
        controller.onUpdateContestantFailed()

        verify(messagesManagerMock).showUnexpectedErrorOccurredMessage()
    }

    private inner class EventPublisherTestDouble : EventPublisher(null) {

        private lateinit var eventPublished: Any

        override fun publish(event: Any): Unit? {
            eventPublished = event
            return null
        }

        fun verifyEventWasPublished(event: ContestantsModifiedEvent) {
            if (event is ContestantsModifiedEvent.ContestantModified)
                compareEvents(event)
            else
                throw RuntimeException("Incorrect event type")
        }

        private fun compareEvents(event: Any) {
            if (event == eventPublished)
                return
            else
                throw RuntimeException("Event was not published")
        }
    }

    private inner class UpdateContestantUseCaseTestDouble : UpdateContestantUseCase(null) {

        private var callCount = 0

        private lateinit var currentContestant: ASMRContestant

        override suspend fun updateContestantTimesSlept(contestant: ASMRContestant) {
            ++callCount
            currentContestant = contestant
        }

        override suspend fun updateContestantTimesDidNotSlept(contestant: ASMRContestant) {
            ++callCount
            currentContestant = contestant
        }

        override suspend fun updateContestantTimesFeltTired(contestant: ASMRContestant) {
            ++callCount
            currentContestant = contestant
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
                    throw RuntimeException("Listener not Unregistered")
            }
        }

        fun verifyUseCaseWasInvokedWith(contestant: ASMRContestant) {
            if (callCount == 1 && currentContestant == contestant)
                return
            else
                throw RuntimeException("Use case invoked with incorrect parameters")
        }
    }
}