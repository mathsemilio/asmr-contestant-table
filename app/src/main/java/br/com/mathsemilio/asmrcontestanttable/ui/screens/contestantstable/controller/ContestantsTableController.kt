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
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase.DeleteContestantsResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase.FetchContestantsResult
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ContestantsModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.PromptDialogEvent
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.ContestantsTableScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantsTableController(
    private val eventSubscriber: EventSubscriber,
    private val messagesManager: MessagesManager,
    private val dialogManager: DialogManager,
    private val coroutineScope: CoroutineScope,
    private val fetchContestantsUseCase: FetchContestantsUseCase,
    private val deleteContestantsUseCase: DeleteContestantsUseCase,
) : ContestantsTableScreenView.Listener,
    EventListener {

    private lateinit var view: ContestantsTableScreenView

    override fun onAddContestantButtonClicked() {
        dialogManager.showAddContestantBottomSheet()
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        dialogManager.showContestantDetailsBottomSheet(contestant)
    }

    private fun fetchContestants() {
        coroutineScope.launch {
            fetchContestantsUseCase.fetchContestants().also { result ->
                handleFetchContestantsResult(result)
            }
        }
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
            PromptDialogEvent.PositiveButtonClicked -> {
                coroutineScope.launch {
                    deleteContestantsUseCase.deleteAllContestants().also { result ->
                        handleDeleteContestantsUseCaseResult(result)
                    }
                }
            }
            PromptDialogEvent.NegativeButtonClicked -> {
                /* no-op associated with this event */
            }
        }
    }

    private fun handleDeleteContestantsUseCaseResult(result: DeleteContestantsResult) {
        when (result) {
            DeleteContestantsResult.Completed -> {
                fetchContestants()
                messagesManager.showAllContestantsDeletedSuccessfullyMessage()
            }
            DeleteContestantsResult.Failed -> {
                view.hideProgressIndicator()
                messagesManager.showUnexpectedErrorOccurredMessage()
            }
        }
    }

    private fun handleFetchContestantsResult(result: FetchContestantsResult) {
        when (result) {
            is FetchContestantsResult.Completed -> {
                result.contestants?.let { contestants ->
                    view.hideProgressIndicator()
                    view.bindContestants(contestants)
                }
            }
            FetchContestantsResult.Failed -> {
                view.hideProgressIndicator()
                messagesManager.showUnexpectedErrorOccurredMessage()
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

    fun bindView(view: ContestantsTableScreenView) {
        this.view = view
    }

    fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchContestants()
    }

    fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
