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

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.ui.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.view.AddContestantView
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase.AddContestantResult

class AddContestantBottomSheetController(
    private val eventPublisher: EventPublisher,
    private val messagesManager: MessagesManager,
    private val addContestantUseCase: AddContestantUseCase
) : AddContestantView.Listener {

    private lateinit var view: AddContestantView

    private val coroutineScope = CoroutineScopeProvider.UIBoundScope

    private var _delegate: AddContestantControllerEventDelegate? = null
    private val delegate: AddContestantControllerEventDelegate
        get() = _delegate!!

    override fun onAddButtonClicked(contestantName: String) {
        coroutineScope.launch {
            view.changeAddButtonState()
            handleAddContestantResult(addContestantUseCase.addContestant(contestantName))
        }
    }

    private fun handleAddContestantResult(result: AddContestantResult) {
        when (result) {
            AddContestantResult.Completed -> {
                delegate.onDismissBottomSheetRequested()
                eventPublisher.publish(ContestantsModifiedEvent.ContestantAdded)
            }
            AddContestantResult.Failed -> {
                view.revertAddButtonState()
                messagesManager.showUnexpectedErrorOccurredMessage()
            }
        }
    }

    fun bindView(view: AddContestantView) {
        this.view = view
    }

    fun addDelegate(delegate: AddContestantControllerEventDelegate) {
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
