package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.ToolbarAction
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.DataModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ToolbarActionEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantsTableScreen : BaseFragment(),
    ContestantsTableContract.View.Listener,
    ContestantsTableContract.Screen,
    FetchContestantsUseCase.Listener,
    DeleteContestantsUseCase.Listener,
    EventPoster.EventListener {

    private lateinit var view: ContestantsTableScreenView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager
    private lateinit var eventPoster: EventPoster

    private lateinit var fetchContestantsUseCase: FetchContestantsUseCase
    private lateinit var deleteContestantsUseCase: DeleteContestantsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        dialogManager = compositionRoot.dialogHelper
        eventPoster = compositionRoot.eventPoster
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

    override fun onAddButtonClicked() {
        dialogManager.showAddContestantBottomSheet()
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        dialogManager.showContestantDetailsBottomSheet(contestant)
    }

    override fun fetchContestants() {
        view.showProgressIndicator()
        coroutineScope.launch { fetchContestantsUseCase.fetchContestants() }
    }

    override fun onToolbarActionClicked(action: ToolbarAction) {
        when (action) {
            ToolbarAction.RESET_CONTEST -> dialogManager.showResetContestDialog {
                coroutineScope.launch { deleteContestantsUseCase.deleteAllContestants() }
            }
        }
    }

    override fun onContestantsFetchedSuccessfully(contestants: List<ASMRContestant>) {
        view.hideProgressIndicator()
        view.bindContestants(contestants)
    }

    override fun onContestantsFetchFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onContestantsDeletedSuccessfully() {
        view.hideProgressIndicator()
        messagesManager.showContestantsDeletedUseCaseSuccessMessage()
    }

    override fun onContestantsDeleteFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is DataModifiedEvent.OnDataModified -> fetchContestants()
            is ToolbarActionEvent -> onToolbarActionClicked(event.action)
        }
    }

    override fun onStart() {
        view.addListener(this)
        fetchContestantsUseCase.addListener(this)
        deleteContestantsUseCase.addListener(this)
        eventPoster.addListener(this)
        fetchContestants()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchContestantsUseCase.removeListener(this)
        deleteContestantsUseCase.removeListener(this)
        eventPoster.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}