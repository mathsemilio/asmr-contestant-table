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
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesDidNotSleptUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesDidNotSleptUseCase.UpdateContestantTimesDidNotSleptResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesFeltTiredUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesFeltTiredUseCase.UpdateContestantTimesFeltTiredResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesSleptUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesSleptUseCase.UpdateContestantTimesSleptResult
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
    private val updateContestantTimesSleptUseCase: UpdateContestantTimesSleptUseCase,
    private val updateContestantTimesDidNotSleptUseCase: UpdateContestantTimesDidNotSleptUseCase,
    private val updateContestantTimesFeltTiredUseCase: UpdateContestantTimesFeltTiredUseCase
) : ContestantDetailsView.Listener {

    private lateinit var view: ContestantDetailsView

    private lateinit var contestant: ASMRContestant

    lateinit var delegate: ContestantDetailsControllerEventDelegate

    override fun onIncrementTimesSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantTimesSleptUseCase.updateContestantTimesSlept(
                contestant
            ).also { result ->
                handleUpdateContestantTimesSleptUseCaseResult(result)
            }
        }
    }

    private fun handleUpdateContestantTimesSleptUseCaseResult(
        result: UpdateContestantTimesSleptResult
    ) {
        when (result) {
            UpdateContestantTimesSleptResult.Completed -> onContestantUpdatedSuccessfully()
            UpdateContestantTimesSleptResult.Failed -> onUpdateContestantFailed()
        }
    }

    private fun onContestantUpdatedSuccessfully() {
        delegate.onDismissBottomSheetRequested()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantModified)
    }

    private fun onUpdateContestantFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onIncrementTimesDidNotSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantTimesDidNotSleptUseCase.updateContestantTimesDidNotSlept(
                contestant
            ).also { result ->
                handleUpdateContestantTimesDidSleptUseCaseResult(result)
            }
        }
    }

    private fun handleUpdateContestantTimesDidSleptUseCaseResult(
        result: UpdateContestantTimesDidNotSleptResult
    ) {
        when (result) {
            UpdateContestantTimesDidNotSleptResult.Completed -> onContestantUpdatedSuccessfully()
            UpdateContestantTimesDidNotSleptResult.Failed -> onUpdateContestantFailed()
        }
    }

    override fun onIncrementTimesFeltTiredButtonClicked() {
        coroutineScope.launch {
            updateContestantTimesFeltTiredUseCase.updateContestantTimesFeltTired(
                contestant
            ).also { result ->
                handleUpdateContestantTimesFeltTiredUseCaseResult(result)
            }
        }
    }

    private fun handleUpdateContestantTimesFeltTiredUseCaseResult(
        result: UpdateContestantTimesFeltTiredResult
    ) {
        when (result) {
            UpdateContestantTimesFeltTiredResult.Completed -> onContestantUpdatedSuccessfully()
            UpdateContestantTimesFeltTiredResult.Failed -> onUpdateContestantFailed()
        }
    }

    fun bindView(view: ContestantDetailsView) {
        this.view = view
    }

    fun bindContestant(contestant: ASMRContestant) {
        this.contestant = contestant
    }

    fun onStart() {
        view.addListener(this)
        view.bindContestant(contestant)
    }

    fun onStop() {
        view.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
