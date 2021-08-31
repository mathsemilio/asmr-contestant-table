package br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.controller

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.PromptDialogEvent
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.view.PromptDialogView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PromptDialogControllerTest {

    companion object {
        private const val DIALOG_TITLE = "Dialog Title"
        private const val DIALOG_MESSAGE = "Dialog Message"
        private const val DIALOG_POSITIVE_BUTTON = "Positive Button"
        private const val DIALOG_NEGATIVE_BUTTON = "Negative Button"
    }

    @Mock
    private lateinit var viewMock: PromptDialogView
    @Mock
    private lateinit var controllerEventListenerMock: PromptDialogControllerEventListener

    private lateinit var eventPublisherTestDouble: EventPublisherTestDouble

    private lateinit var controller: PromptDialogController

    @Before
    fun setUp() {
        eventPublisherTestDouble = EventPublisherTestDouble()

        controller = PromptDialogController(eventPublisherTestDouble)

        controller.run {
            bindView(viewMock)
            bindTitle(DIALOG_TITLE)
            bindMessage(DIALOG_MESSAGE)
            bindPositiveButtonText(DIALOG_POSITIVE_BUTTON)
            bindNegativeButtonText(DIALOG_NEGATIVE_BUTTON)
            registerForControllerEvents(controllerEventListenerMock)
        }
    }

    @Test
    fun onStart_viewListenerRegistered() {
        controller.onStart()

        verify(viewMock).addListener(controller)
    }

    @Test
    fun onStop_viewListenerRegistered() {
        controller.onStop()

        verify(viewMock).removeListener(controller)
    }

    @Test
    fun onPositiveButtonClicked_positiveButtonClickedEventPublished() {
        controller.onPositiveButtonClicked()

        verify(controllerEventListenerMock).onPromptDialogButtonClicked()
        eventPublisherTestDouble.verifyEventWasPublished(PromptDialogEvent.PositiveButtonClicked)
    }

    @Test
    fun onNegativeButtonClicked_negativeButtonClickedEventPublished() {
        controller.onNegativeButtonClicked()

        verify(controllerEventListenerMock).onPromptDialogButtonClicked()
        eventPublisherTestDouble.verifyEventWasPublished(PromptDialogEvent.NegativeButtonClicked)
    }

    private inner class EventPublisherTestDouble : EventPublisher(null) {

        private lateinit var eventPublished: Any

        override fun publish(event: Any): Unit? {
            eventPublished = event
            return null
        }

        fun verifyEventWasPublished(event: PromptDialogEvent) {
            when (event) {
                PromptDialogEvent.PositiveButtonClicked -> compareEvents(event)
                PromptDialogEvent.NegativeButtonClicked -> compareEvents(event)
            }
        }

        private fun compareEvents(event: Any) {
            if (event == eventPublished)
                return
            else
                throw RuntimeException("Event was not published")
        }
    }
}