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

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.ui.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.view.AddWeekHighlightsView
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase.AddWeekHighlightsResult

class AddWeekHighlightsBottomSheetController(
    private val eventPublisher: EventPublisher,
    private val messagesManager: MessagesManager,
    private val addWeekHighlightsUseCase: AddWeekHighlightsUseCase
) : AddWeekHighlightsView.Listener {

    private lateinit var view: AddWeekHighlightsView

    private val coroutineScope = CoroutineScopeProvider.UIBoundScope

    private var _delegate: WeekHighlightsControllerEventDelegate? = null
    private val delegate: WeekHighlightsControllerEventDelegate
        get() = _delegate!!

    override fun onAddButtonClicked(contestantsNames: List<String>) {
        coroutineScope.launch {
            view.changeAddButtonState()
            handleAddHighlightsResult(addWeekHighlightsUseCase.addWeekHighlights(contestantsNames))
        }
    }

    private fun handleAddHighlightsResult(result: AddWeekHighlightsResult) {
        when (result) {
            AddWeekHighlightsResult.Completed -> {
                delegate.onDismissBottomSheetRequested()
                eventPublisher.publish(WeekHighlightsModifiedEvent.WeekHighlightAdded)
            }
            AddWeekHighlightsResult.Failed -> {
                view.revertAddButtonState()
                messagesManager.showUnexpectedErrorOccurredMessage()
            }
        }
    }

    fun bindView(view: AddWeekHighlightsView) {
        this.view = view
    }

    fun addDelegate(delegate: WeekHighlightsControllerEventDelegate) {
        _delegate = delegate
    }

    fun removeDelegate() {
        _delegate = null
    }

    fun onStart() = view.addObserver(this)

    fun onStop() {
        view.removeObserver(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
