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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.controller

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.view.AddWeekHighlightsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddWeekHighlightsBottomSheetController(
    private val messagesManager: MessagesManager,
    private val eventPublisher: EventPublisher,
    private val coroutineScope: CoroutineScope,
    private val addWeekHighlightsUseCase: AddWeekHighlightsUseCase
) : AddWeekHighlightsView.Listener,
    AddWeekHighlightsUseCase.Listener {

    private lateinit var view: AddWeekHighlightsView

    private lateinit var listener: WeekHighlightsControllerEventListener

    override fun onAddButtonClicked(firstContestantName: String, secondContestantName: String) {
        coroutineScope.launch {
            view.changeAddButtonState()
            addWeekHighlightsUseCase.insertWeekHighlights(firstContestantName, secondContestantName)
        }
    }

    override fun onWeekHighlightsAddedSuccessfully() {
        listener.onWeekHighlightsModified()
        eventPublisher.publish(WeekHighlightsModifiedEvent.WeekHighlightAdded)
    }

    override fun onAddWeekHighlightsFailed() {
        view.revertAddButtonState()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    fun bindView(view: AddWeekHighlightsView) {
        this.view = view
    }

    fun registerForControllerEvents(listener: WeekHighlightsControllerEventListener) {
        this.listener = listener
    }

    fun onStart() {
        view.addListener(this)
        addWeekHighlightsUseCase.addListener(this)
    }

    fun onStop() {
        view.removeListener(this)
        addWeekHighlightsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}