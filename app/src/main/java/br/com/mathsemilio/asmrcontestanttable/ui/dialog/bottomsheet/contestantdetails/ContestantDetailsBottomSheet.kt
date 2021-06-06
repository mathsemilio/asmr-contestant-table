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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.ARG_CONTESTANT
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantDetailsBottomSheet : BaseBottomSheetDialogFragment(),
    ContestantDetailsView.Listener,
    UpdateContestantUseCase.Listener {

    companion object {
        fun withContestant(contestant: ASMRContestant): ContestantDetailsBottomSheet {
            val args = Bundle(1).apply { putSerializable(ARG_CONTESTANT, contestant) }
            val contestantDetailsBottomSheet = ContestantDetailsBottomSheet()
            contestantDetailsBottomSheet.arguments = args
            return contestantDetailsBottomSheet
        }
    }

    private lateinit var view: ContestantDetailsView

    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPublisher: EventPublisher
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var updateContestantUseCase: UpdateContestantUseCase

    private lateinit var currentContestant: ASMRContestant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesManager = compositionRoot.messagesManager
        eventPublisher = compositionRoot.eventPublisher
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        updateContestantUseCase = compositionRoot.updateContestantUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getContestantsDetailsView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentContestant = getContestantFromBundle()
    }

    private fun getContestantFromBundle(): ASMRContestant {
        return requireArguments().getSerializable(ARG_CONTESTANT) as ASMRContestant
    }

    override fun onIncrementTimesSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesSlept(currentContestant)
        }
    }

    override fun onIncrementTimesDidNotSleptButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesDidNotSlept(currentContestant)
        }
    }

    override fun onIncrementTimesFeltTiredButtonClicked() {
        coroutineScope.launch {
            updateContestantUseCase.updateContestantTimesFeltTired(currentContestant)
        }
    }

    override fun onContestantUpdatedSuccessfully() {
        dismiss()
        eventPublisher.publish(ContestantsModifiedEvent.ContestantModified)
    }

    override fun onUpdateContestantFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onStart() {
        view.addListener(this)
        updateContestantUseCase.addListener(this)
        view.bindContestant(currentContestant)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        updateContestantUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}