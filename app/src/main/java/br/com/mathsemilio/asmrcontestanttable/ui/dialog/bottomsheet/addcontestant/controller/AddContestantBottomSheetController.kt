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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.controller

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view.AddContestantView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddContestantBottomSheetController(
    private val messagesManager: MessagesManager,
    private val coroutineScope: CoroutineScope,
    private val eventPublisher: EventPublisher,
    private val addContestantUseCase: AddContestantUseCase
) : AddContestantView.Listener,
    AddContestantUseCase.Listener {

    private lateinit var view: AddContestantView

    private lateinit var listener: ContestantsControllerEventListener

    override fun onAddButtonClicked(contestantName: String) {
        coroutineScope.launch {
            view.changeAddButtonState()
            addContestantUseCase.addContestant(contestantName)
        }
    }

    override fun onContestantAddedSuccessfully() {
        listener.onContestantsModified()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantAdded)
    }

    override fun onAddContestantFailed() {
        view.revertAddButtonState()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    fun bindView(view: AddContestantView) {
        this.view = view
    }

    fun registerForControllerEvents(listener: ContestantsControllerEventListener) {
        this.listener = listener
    }

    fun onStart() {
        view.addListener(this)
        addContestantUseCase.addListener(this)
    }

    fun onStop() {
        view.removeListener(this)
        addContestantUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}