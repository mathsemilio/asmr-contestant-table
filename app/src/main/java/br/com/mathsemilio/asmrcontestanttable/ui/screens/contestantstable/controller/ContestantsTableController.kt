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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.controller

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventListener
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.delete.DeleteContestantsUseCaseImpl
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.fetch.FetchContestantsUseCaseImpl
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.PromptDialogEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantsTableController(
    private val eventSubscriber: EventSubscriber,
    private val messagesManager: MessagesManager,
    private val dialogManager: DialogManager,
    private val fetchContestantsUseCase: FetchContestantsUseCaseImpl,
    private val deleteContestantsUseCase: DeleteContestantsUseCaseImpl,
) : ContestantsTableScreenView.Listener,
    FetchContestantsUseCaseImpl.Listener,
    DeleteContestantsUseCaseImpl.Listener,
    EventListener {

    private lateinit var view: ContestantsTableScreenView

    private lateinit var coroutineScope: CoroutineScope

    fun bindView(view: ContestantsTableScreenView) {
        this.view = view
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

    private fun fetchContestants() {
        coroutineScope.launch {
            fetchContestantsUseCase.fetchContestants()
        }
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
            PromptDialogEvent.NegativeButtonClicked -> {
                /* no-op associated with this event */
            }
        }
    }

    fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contestants_table_screen_toolbar, menu)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_reset_contest -> {
                dialogManager.showResetContestDialog()
                true
            }
            else -> false
        }
    }

    fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchContestantsUseCase.addListener(this)
        deleteContestantsUseCase.addListener(this)
        fetchContestants()
    }

    fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchContestantsUseCase.removeListener(this)
        deleteContestantsUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}