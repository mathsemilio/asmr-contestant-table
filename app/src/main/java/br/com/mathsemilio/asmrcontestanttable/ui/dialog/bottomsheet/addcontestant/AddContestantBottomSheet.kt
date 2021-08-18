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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.add.AddContestantUseCaseImpl
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddContestantBottomSheet : BaseBottomSheetDialogFragment(),
    AddContestantView.Listener,
    AddContestantUseCaseImpl.Listener {

    private lateinit var view: AddContestantView

    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var eventPublisher: EventPublisher

    private lateinit var addContestantUseCase: AddContestantUseCaseImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventPublisher = compositionRoot.eventPublisher
        addContestantUseCase = compositionRoot.addContestantUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAddContestView(container)
        return view.rootView
    }

    override fun onAddButtonClicked(contestantName: String) {
        coroutineScope.launch {
            view.changeAddButtonState()
            addContestantUseCase.addContestant(contestantName)
        }
    }

    override fun onContestantAddedSuccessfully() {
        dismiss()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantAdded)
    }

    override fun onAddContestantFailed() {
        view.revertAddButtonState()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onStart() {
        view.addListener(this)
        addContestantUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addContestantUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}