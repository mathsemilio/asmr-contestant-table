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
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.DataModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddWeekHighlightsBottomSheet : BaseBottomSheetDialogFragment(),
    AddWeekHighlightsContract.View.Listener,
    AddWeekHighlightsUseCase.Listener {

    private lateinit var view: AddWeekHighlightsView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPoster: EventPoster

    private lateinit var addWeekHighlightsUseCase: AddWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        eventPoster = compositionRoot.eventPoster
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
        view.changeAddButtonState()
        coroutineScope.launch {
            addWeekHighlightsUseCase.insertWeekHighlights(firstContestantName, secondContestantName)
        }
    }

    override fun onWeekHighlightsAddedSuccessfully() {
        dismiss()
        eventPoster.postEvent(DataModifiedEvent.OnDataModified)
    }

    override fun onWeekHighlightsAddFailed(errorMessage: String) {
        view.revertAddButtonState()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onStart() {
        view.addListener(this)
        addWeekHighlightsUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addWeekHighlightsUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}