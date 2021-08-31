package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.testdata.WeekHighlightsTestDataProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsView
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
class WeekHighlightsControllerTest {

    companion object {
        private val WEEK_HIGHLIGHTS = WeekHighlightsTestDataProvider.weekHighlights
    }

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var view: WeekHighlightsView
    @Mock
    private lateinit var dialogManager: DialogManager
    @Mock
    private lateinit var messagesManager: MessagesManager

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchWeekHighlightsUseCaseTestDouble: FetchWeekHighlightsUseCaseTestDouble
    private lateinit var eventSubscriberTestDouble: EventSubscriberTestDouble

    private lateinit var controller: WeekHighlightsController

    @Before
    fun setUp() {
        fetchWeekHighlightsUseCaseTestDouble = FetchWeekHighlightsUseCaseTestDouble()
        eventSubscriberTestDouble = EventSubscriberTestDouble()

        coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        controller = WeekHighlightsController(
            messagesManager,
            eventSubscriberTestDouble,
            dialogManager,
            coroutineScope,
            fetchWeekHighlightsUseCaseTestDouble
        )

        controller.bindView(view)
    }

    @Test
    fun onStart_registerListeners_listenersRegistered() = runBlockingTest {
        controller.onStart()

        verify(view).addListener(controller)
        eventSubscriberTestDouble.verifyListenerRegistered(controller)
        fetchWeekHighlightsUseCaseTestDouble.verifyListenerRegistered(controller)
    }

    @Test
    fun onStart_fetchWeekHighlightsSuccess_highlightsBoundToView() = runBlockingTest {
        fetchWeekHighlightsUseCaseTestDouble.arrangeSuccess()

        controller.onStart()

        verify(view).hideProgressIndicator()
        verify(view).bindWeekHighlights(WEEK_HIGHLIGHTS)
    }

    @Test
    fun onStart_fetchWeekHighlightsFailed_highlightsNotBoundToView() = runBlockingTest {
        fetchWeekHighlightsUseCaseTestDouble.arrangeFailure()

        controller.onStart()

        verify(view).hideProgressIndicator()
        verify(view, never()).bindWeekHighlights(anyList())
    }

    @Test
    fun onStop_unregisterListeners_listenersUnregistered() {
        controller.onStop()

        verify(view).removeListener(controller)
        eventSubscriberTestDouble.verifyListenerNotRegistered(controller)
        fetchWeekHighlightsUseCaseTestDouble.verifyListenerUnregistered(controller)
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

        fun verifyListenerRegistered(candidate: WeekHighlightsController) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    return
            }

            throw RuntimeException("Listener not registered")
        }

        fun verifyListenerNotRegistered(candidate: WeekHighlightsController) {
            listeners.forEach { listener ->
                if (listener == candidate)
                    throw RuntimeException("Listener not unregistered")
            }
        }
    }

    inner class FetchWeekHighlightsUseCaseTestDouble : FetchWeekHighlightsUseCase(null) {

        private var isSuccessArranged = false

        override suspend fun fetchWeekHighlights() = runBlockingTest {
            if (isSuccessArranged)
                notifyListenerOnWeekHighlightsFetchedSuccessfully()
            else
                notifyListenerOnFetchWeekHighlightsFailed()
        }

        private fun notifyListenerOnWeekHighlightsFetchedSuccessfully() {
            notifyListener { listener ->
                listener.onWeekHighlightsFetchedSuccessfully(WEEK_HIGHLIGHTS)
            }
        }

        private fun notifyListenerOnFetchWeekHighlightsFailed() {
            notifyListener { listener ->
                listener.onWeekHighlightsFetchFailed()
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