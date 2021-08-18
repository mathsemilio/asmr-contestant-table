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

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class WeekHighlightsController(
    private val messagesManager: MessagesManager,
    private val eventSubscriber: EventSubscriber,
    private val dialogManager: DialogManager,
    private val coroutineScope: CoroutineScope,
    private val fetchWeekHighlightsUseCase: FetchWeekHighlightsUseCase
) : WeekHighlightsView.Listener,
    FetchWeekHighlightsUseCase.Listener,
    EventListener {

    private lateinit var view: WeekHighlightsView

    override fun onAddWeekHighlightsButtonClicked() {
        dialogManager.showAddWeekHighlightsBottomSheet()
    }

    override fun onWeekHighlightsFetchedSuccessfully(weekHighlights: List<WeekHighlights>) {
        view.hideProgressIndicator()
        view.bindWeekHighlights(weekHighlights)
    }

    override fun onWeekHighlightsFetchFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is WeekHighlightsModifiedEvent.WeekHighlightAdded -> fetchWeekHighlights()
        }
    }

    private fun fetchWeekHighlights() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchWeekHighlightsUseCase.fetchWeekHighlights()
        }
    }

    fun bindView(view: WeekHighlightsView) {
        this.view = view
    }

    fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchWeekHighlightsUseCase.addListener(this)
        fetchWeekHighlights()
    }

    fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchWeekHighlightsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}