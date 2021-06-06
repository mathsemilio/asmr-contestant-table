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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.os.Bundle
import android.view.*
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.PromptDialogEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantsTableFragment : BaseFragment(),
    ContestantsTableScreenView.Listener,
    FetchContestantsUseCase.Listener,
    DeleteContestantsUseCase.Listener,
    EventListener {

    private lateinit var view: ContestantsTableScreenView

    private lateinit var eventSubscriber: EventSubscriber
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchContestantsUseCase: FetchContestantsUseCase
    private lateinit var deleteContestantsUseCase: DeleteContestantsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        eventSubscriber = compositionRoot.eventSubscriber
        messagesManager = compositionRoot.messagesManager
        dialogManager = compositionRoot.dialogManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        fetchContestantsUseCase = compositionRoot.fetchContestantsUseCase
        deleteContestantsUseCase = compositionRoot.deleteContestantsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getContestantsTableScreenView(container)
        return view.rootView
    }

    private fun fetchContestants() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchContestantsUseCase.fetchContestants()
        }
    }

    override fun onAddContestantButtonClicked() {
        dialogManager.showAddContestantBottomSheet()
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        dialogManager.showContestantDetailsBottomSheet(contestant)
    }

    override fun onContestantsFetchedSuccessfully(contestants: List<ASMRContestant>) {
        view.hideProgressIndicator()
        view.bindContestants(contestants)
    }

    override fun onContestantsFetchFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onAllContestantsDeletedSuccessfully() {
        fetchContestants()
        messagesManager.showAllContestantsDeletedSuccessfullyMessage()
    }

    override fun onDeleteAllContestantsFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ContestantsModifiedEvent.ContestantAdded -> fetchContestants()
            is ContestantsModifiedEvent.ContestantModified -> fetchContestants()
            is PromptDialogEvent -> handlePromptDialogEvent(event)
        }
    }

    private fun handlePromptDialogEvent(event: PromptDialogEvent) {
        when (event) {
            PromptDialogEvent.PositiveButtonClicked -> coroutineScope.launch {
                deleteContestantsUseCase.deleteAllContestants()
            }
            PromptDialogEvent.NegativeButtonClicked -> { /* no-op - Dialog canceled */ }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contestants_table_screen_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_reset_contest -> {
                dialogManager.showResetContestDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchContestantsUseCase.addListener(this)
        deleteContestantsUseCase.addListener(this)
        fetchContestants()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchContestantsUseCase.removeListener(this)
        deleteContestantsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}