package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.INVALID_EVENT
import br.com.mathsemilio.asmrcontestanttable.common.INVALID_OPERATION
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ModelModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ToolbarEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
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
        coroutineScope.launch { fetchContestantsUseCase.fetchContestants() }
    }

    override fun onContestantsFetchStarted() {
        view.showProgressIndicator()
    }

    override fun onContestantsFetchCompleted(contestants: List<ASMRContestant>) {
        view.hideProgressIndicator()
        view.bindContestants(contestants)
    }

    override fun onContestantsFetchFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showFetchContestantsUseCaseErrorMessage(errorMessage)
    }

    override fun onContestantsDeleteCompleted() {
        messagesManager.showAllContestantsDeleteUseCaseSuccessMessage()
        fetchContestants()
    }

    override fun onContestantsDeleteFailed(errorMessage: String) {
        messagesManager.showAllContestantsDeletedUseCaseErrorMessage(errorMessage)
    }

    override fun onContestantModifiedEvent(event: ModelModifiedEvent.Event) {
        when (event) {
            ModelModifiedEvent.Event.CONTESTANTS_MODIFIED -> fetchContestants()
            else -> throw IllegalArgumentException(INVALID_EVENT)
        }
    }

    override fun onToolbarActionResetContestClicked(event: ToolbarEvent.Event) {
        when (event) {
            ToolbarEvent.Event.RESET_CONTEST_ACTION_CLICKED ->
                dialogManager.showResetContestDialog {
                    coroutineScope.launch { deleteContestantsUseCase.deleteAllContestants() }
                }
        }
    }

    override fun onFetchContestantsUseCaseEvent(result: OperationResult<List<ASMRContestant>>) {
        when (result) {
            OperationResult.OnStarted ->
                onContestantsFetchStarted()
            is OperationResult.OnCompleted ->
                onContestantsFetchCompleted(result.data ?: throw NullPointerException())
            is OperationResult.OnError ->
                onContestantsFetchFailed(result.errorMessage!!)
        }
    }

    override fun onDeleteContestantsUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            is OperationResult.OnCompleted -> onContestantsDeleteCompleted()
            is OperationResult.OnError -> onContestantsDeleteFailed(result.errorMessage!!)
            else -> throw IllegalArgumentException(INVALID_OPERATION)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ModelModifiedEvent -> onContestantModifiedEvent(event.modelModifiedEvent)
            is ToolbarEvent -> onToolbarActionResetContestClicked(event.toolbarEvent)
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