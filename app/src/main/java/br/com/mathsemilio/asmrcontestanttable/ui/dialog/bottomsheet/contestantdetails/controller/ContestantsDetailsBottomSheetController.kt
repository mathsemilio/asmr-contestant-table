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

import kotlinx.coroutines.*
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.view.ContestantDetailsView
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesSleptUseCase.UpdateContestantTimesSleptResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesFeltTiredUseCase.UpdateContestantTimesFeltTiredResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesDidNotSleptUseCase.UpdateContestantTimesDidNotSleptResult

class ContestantsDetailsBottomSheetController(
    private val eventPublisher: EventPublisher,
    private val messagesManager: MessagesManager,
    private val updateContestantTimesSleptUseCase: UpdateContestantTimesSleptUseCase,
    private val updateContestantTimesFeltTiredUseCase: UpdateContestantTimesFeltTiredUseCase,
    private val updateContestantTimesDidNotSleptUseCase: UpdateContestantTimesDidNotSleptUseCase
) : ContestantDetailsView.Listener {

    private lateinit var view: ContestantDetailsView

    private val coroutineScope = CoroutineScopeProvider.UIBoundScope

    private lateinit var contestant: ASMRContestant

    private var _newDelegate: ContestantDetailsControllerEventDelegate? = null
    private val delegate: ContestantDetailsControllerEventDelegate
        get() = _newDelegate!!

    override fun onIncrementTimesSleptButtonClicked() {
        coroutineScope.launch {
            handleUpdateContestantTimesSleptResult(
                updateContestantTimesSleptUseCase.updateContestantTimesSlept(contestant)
            )
        }
    }

    private fun handleUpdateContestantTimesSleptResult(
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
            handleUpdateContestantTimesDidSleptResult(
                updateContestantTimesDidNotSleptUseCase.updateContestantTimesDidNotSlept(contestant)
            )
        }
    }

    private fun handleUpdateContestantTimesDidSleptResult(
        result: UpdateContestantTimesDidNotSleptResult
    ) {
        when (result) {
            UpdateContestantTimesDidNotSleptResult.Completed -> onContestantUpdatedSuccessfully()
            UpdateContestantTimesDidNotSleptResult.Failed -> onUpdateContestantFailed()
        }
    }

    override fun onIncrementTimesFeltTiredButtonClicked() {
        coroutineScope.launch {
            handleUpdateContestantTimesFeltTiredResult(
                updateContestantTimesFeltTiredUseCase.updateContestantTimesFeltTired(contestant)
            )
        }
    }

    private fun handleUpdateContestantTimesFeltTiredResult(
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

    fun addDelegate(delegate: ContestantDetailsControllerEventDelegate) {
        _newDelegate = delegate
    }

    fun removeDelegate() {
        _newDelegate = null
    }

    fun onStart() {
        view.addObserver(this)
        view.bindContestant(contestant)
    }

    fun onStop() {
        view.removeObserver(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
