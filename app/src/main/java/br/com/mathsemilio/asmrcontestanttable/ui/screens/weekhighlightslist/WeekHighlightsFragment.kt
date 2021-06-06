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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view.WeekHighlightsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class WeekHighlightsFragment : BaseFragment(),
    WeekHighlightsView.Listener,
    FetchWeekHighlightsUseCase.Listener,
    EventListener {

    private lateinit var view: WeekHighlightsView

    private lateinit var messagesManager: MessagesManager
    private lateinit var eventSubscriber: EventSubscriber
    private lateinit var dialogManager: DialogManager

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchWeekHighlightsUseCase: FetchWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesManager
        eventSubscriber = compositionRoot.eventSubscriber
        dialogManager = compositionRoot.dialogManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        fetchWeekHighlightsUseCase = compositionRoot.fetchWeekHighlightsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getWeekHighlightsListScreenView(container)
        return view.rootView
    }

    private fun fetchWeekHighlights() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchWeekHighlightsUseCase.fetchWeekHighlights()
        }
    }

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

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchWeekHighlightsUseCase.addListener(this)
        fetchWeekHighlights()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchWeekHighlightsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}