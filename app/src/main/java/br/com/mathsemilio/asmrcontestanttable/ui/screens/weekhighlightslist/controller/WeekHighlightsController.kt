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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.controller

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsView
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase.FetchWeekHighlightsResult

class WeekHighlightsController(
    private val dialogManager: DialogManager,
    private val messagesManager: MessagesManager,
    private val eventSubscriber: EventSubscriber,
    private val fetchWeekHighlightsUseCase: FetchWeekHighlightsUseCase
) : WeekHighlightsView.Listener,
    EventListener {

    private lateinit var view: WeekHighlightsView

    private val coroutineScope = CoroutineScopeProvider.UIBoundScope

    override fun onAddWeekHighlightsButtonClicked() {
        dialogManager.showAddWeekHighlightsBottomSheet()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is WeekHighlightsModifiedEvent.WeekHighlightAdded -> fetchWeekHighlights()
        }
    }

    private fun fetchWeekHighlights() {
        coroutineScope.launch {
            view.showProgressIndicator()
            handleFetchWeekHighlightsResult(fetchWeekHighlightsUseCase.fetchWeekHighlights())
        }
    }

    private fun handleFetchWeekHighlightsResult(result: FetchWeekHighlightsResult) {
        when (result) {
            is FetchWeekHighlightsResult.Completed -> {
                view.hideProgressIndicator()
                view.bindWeekHighlights(result.highlights)
            }
            FetchWeekHighlightsResult.Failed -> {
                view.hideProgressIndicator()
                messagesManager.showUnexpectedErrorOccurredMessage()
            }
        }
    }

    fun bindView(view: WeekHighlightsView) {
        this.view = view
    }

    fun onStart() {
        view.addObserver(this)
        eventSubscriber.subscribe(this)
        fetchWeekHighlights()
    }

    fun onStop() {
        view.removeObserver(this)
        eventSubscriber.unsubscribe(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
