package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.testdata.ContestantsTestDataProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenView
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContestantsTableControllerTest {

    companion object {
        private val CONTESTANTS = ContestantsTestDataProvider.contestants
        private val CONTESTANT = ContestantsTestDataProvider.contestants[0]
    }

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var viewMock: ContestantsTableScreenView
    @Mock
    private lateinit var messagesManagerMock: MessagesManager
    @Mock
    private lateinit var dialogManagerMock: DialogManager

    private lateinit var deleteContestantsUseCaseTestDouble: DeleteContestantsUseCaseTestDouble
    private lateinit var fetchContestantsUseCaseTestDouble: FetchContestantsUseCaseTestDouble
    private lateinit var eventSubscriberTestDouble: EventSubscriberTestDouble

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var controller: ContestantsTableController

    @Before
    fun setUp() {
        deleteContestantsUseCaseTestDouble = DeleteContestantsUseCaseTestDouble()
        fetchContestantsUseCaseTestDouble = FetchContestantsUseCaseTestDouble()
        eventSubscriberTestDouble = EventSubscriberTestDouble()

        coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        controller = ContestantsTableController(
            eventSubscriberTestDouble,
            messagesManagerMock,
            dialogManagerMock,
            coroutineScope,
            fetchContestantsUseCaseTestDouble,
            deleteContestantsUseCaseTestDouble
        )

        controller.bindView(viewMock)
    }

    @Test
    fun onStart_listenersRegistered() {
        controller.onStart()

        verify(viewMock).addListener(controller)
        deleteContestantsUseCaseTestDouble.verifyListenerRegistered(controller)
        fetchContestantsUseCaseTestDouble.verifyListenerRegistered(controller)
        eventSubscriberTestDouble.verifyListenerSubscription(controller)
    }

    @Test
    fun onStart_contestantsFetchedSuccessfully_contestantsBoundToView() = runBlockingTest {
        fetchContestantsUseCaseTestDouble.arrangeSuccess()

        controller.onStart()

        verify(viewMock).hideProgressIndicator()
        verify(viewMock).bindContestants(CONTESTANTS)
    }

    @Test
    fun onStart_failedToFetchContestants_contestantsNotBoundToView() = runBlockingTest {
        fetchContestantsUseCaseTestDouble.arrangeFailure()

        controller.onStart()

        verify(viewMock).hideProgressIndicator()
        verify(viewMock, never()).bindContestants(anyList())
    }

    @Test
    fun onStop_listenersUnregistered() {
        controller.onStop()

        verify(viewMock).removeListener(controller)
        deleteContestantsUseCaseTestDouble.verifyListenerUnregistered(controller)
        fetchContestantsUseCaseTestDouble.verifyListenerUnregistered(controller)
        eventSubscriberTestDouble.verifyListenerUnsubscribed(controller)
    }

    @Test
    fun onAddContestantButtonClicked_showsAddContestantBottomSheet() {
        controller.onAddContestantButtonClicked()

        verify(dialogManagerMock).showAddContestantBottomSheet()
    }

    @Test
    fun onContestantClicked_showsContestantDetailsBottomSheet() {
        controller.onContestantClicked(CONTESTANT)

        verify(dialogManagerMock).showContestantDetailsBottomSheet(CONTESTANT)
    }

    inner class DeleteContestantsUseCaseTestDouble : DeleteContestantsUseCase(null) {

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
    }

    inner class EventSubscriberTestDouble : EventSubscriber(null) {

        private val listeners = mutableListOf<EventListener>()

        override fun subscribe(listener: EventListener): Unit? {
            if (!listeners.contains(listener))
                listeners.add(listener)

            return null
        }

        override fun unsubscribe(listener: EventListener): Unit? {
            if (listeners.contains(listener))
                listeners.remove(listener)

            return null
        }

        fun verifyListenerSubscription(candidate: ContestantsTableController) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    return
            }

            throw RuntimeException("Listener not subscribed")
        }

        fun verifyListenerUnsubscribed(candidate: EventListener) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    throw RuntimeException("Listener not unsubscribed")
            }
        }
    }

    inner class FetchContestantsUseCaseTestDouble : FetchContestantsUseCase(null) {

        private var isSuccessArranged = false

        override suspend fun fetchContestants() {
            if (isSuccessArranged)
                onSuccessArranged()
            else
                onFailureArranged()
        }

        private fun onSuccessArranged() {
            notifyListener { listener ->
                listener.onContestantsFetchedSuccessfully(CONTESTANTS)
            }
        }

        private fun onFailureArranged() {
            notifyListener { listener ->
                listener.onContestantsFetchFailed()
            }
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

        fun arrangeSuccess() {
            isSuccessArranged = true
        }
        fun arrangeFailure() {
            isSuccessArranged = false
        }
    }
}