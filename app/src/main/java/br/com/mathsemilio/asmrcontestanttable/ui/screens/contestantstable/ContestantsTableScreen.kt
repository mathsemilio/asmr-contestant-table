package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ContestantsTableScreen : BaseFragment(),
    ContestantsTableContract.View.Listener,
    EventObserver<OperationResult<List<ASMRContestant>>> {

    private lateinit var view: ContestantsTableScreenView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesHelper: MessagesHelper
    private lateinit var dialogHelper: DialogHelper

    private lateinit var fetchContestantsUseCase: FetchContestantsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope

        messagesHelper = compositionRoot.messagesHelper

        dialogHelper = compositionRoot.dialogHelper

        fetchContestantsUseCase = compositionRoot.fetchContestantsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getContestantsTableScreenView(container)
        return view.rootView
    }

    private fun getContestants() {
        coroutineScope.launch { fetchContestantsUseCase.getAllContestants() }
    }

    override fun onAddButtonClicked() {
        dialogHelper.showAddContestantBottomSheet()
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        TODO("Not yet implemented")
    }

    override fun onNoContestantsRegistered() {
        dialogHelper.showNoContestantsRegisteredDialog {
            dialogHelper.showSetContestantsNumberDialog()
        }
    }

    override fun onEvent(event: OperationResult<List<ASMRContestant>>) {
        when (event) {
            OperationResult.OnStarted ->
                onFetchContestantsStarted()
            is OperationResult.OnCompleted ->
                onFetchContestantsCompleted(event.data ?: throw NullPointerException())
            is OperationResult.OnError ->
                onFetchContestantsError(event.errorMessage!!)
        }
    }

    private fun onFetchContestantsStarted() {
        view.showProgressIndicator()
    }

    private fun onFetchContestantsCompleted(contestants: List<ASMRContestant>) {
        view.hideProgressIndicator()
        view.bindContestants(contestants)
    }

    private fun onFetchContestantsError(errorMessage: String) {
        view.hideProgressIndicator()
        messagesHelper.showFetchContestantsUseCaseErrorMessage(errorMessage)
    }

    override fun onStart() {
        view.addListener(this)
        fetchContestantsUseCase.addListener(this)
        getContestants()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchContestantsUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.cancel()
        super.onDestroyView()
    }
}