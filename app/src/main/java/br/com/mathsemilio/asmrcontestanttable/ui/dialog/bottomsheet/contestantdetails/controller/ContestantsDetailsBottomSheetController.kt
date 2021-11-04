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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.controller

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.view.ContestantDetailsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantsDetailsBottomSheetController(
    private val messagesManager: MessagesManager,
    private val eventPublisher: EventPublisher,
    private val coroutineScope: CoroutineScope,
    private val updateContestantUseCase: UpdateContestantUseCase
) : ContestantDetailsView.Listener,
    UpdateContestantUseCase.Listener {

    private lateinit var view: ContestantDetailsView

    private lateinit var contestant: ASMRContestant

    private lateinit var delegate: ContestantDetailsControllerEventDelegate

    override fun onIncrementTimesSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesSlept(contestant)
        }
    }

    override fun onIncrementTimesDidNotSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesDidNotSlept(contestant)
        }
    }

    override fun onIncrementTimesFeltTiredButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesFeltTired(contestant)
        }
    }

    override fun onContestantUpdatedSuccessfully() {
        delegate.onDismissBottomSheetRequested()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantModified)
    }

    override fun onUpdateContestantFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    fun bindView(view: ContestantDetailsView) {
        this.view = view
    }

    fun bindContestant(contestant: ASMRContestant) {
        this.contestant = contestant
    }

    fun addEventDelegate(delegate: ContestantDetailsControllerEventDelegate) {
        this.delegate = delegate
    }

    fun onStart() {
        view.addListener(this)
        updateContestantUseCase.addListener(this)
        view.bindContestant(contestant)
    }

    fun onStop() {
        view.removeListener(this)
        updateContestantUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}