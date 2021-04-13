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
package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.WeekHighlightsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddWeekHighlightsBottomSheet : BaseBottomSheetDialogFragment(),
    AddWeekHighlightsView.Listener,
    AddWeekHighlightsUseCase.Listener {

    private lateinit var view: AddWeekHighlightsView

    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPublisher: EventPublisher
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var addWeekHighlightsUseCase: AddWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesHelper
        eventPublisher = compositionRoot.eventPublisher
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        addWeekHighlightsUseCase = compositionRoot.addWeekHighlightsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAddWeekHighlightsView(container)
        return view.rootView
    }

    override fun onAddButtonClicked(firstContestantName: String, secondContestantName: String) {
        coroutineScope.launch {
            view.changeAddButtonState()
            addWeekHighlightsUseCase.insertWeekHighlights(firstContestantName, secondContestantName)
        }
    }

    override fun onWeekHighlightsAddedSuccessfully() {
        dismiss()
        eventPublisher.publish(WeekHighlightsModifiedEvent.OnWeekHighlightAdded)
    }

    override fun onAddWeekHighlightsFailed() {
        view.revertAddButtonState()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onStart() {
        view.addListener(this)
        addWeekHighlightsUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addWeekHighlightsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}