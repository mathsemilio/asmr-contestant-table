package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ModelModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddContestantBottomSheet : BaseBottomSheetDialogFragment(),
    AddContestantContract.View.Listener,
    AddContestantContract.BottomSheet,
    AddContestantUseCase.Listener {

    private lateinit var view: AddContestantView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPoster: EventPoster

    private lateinit var addContestantUseCase: AddContestantUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        eventPoster = compositionRoot.eventPoster
        addContestantUseCase = compositionRoot.addContestantUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAddContestView(container)
        return view.rootView
    }

    override fun onAddButtonClicked(contestantName: String) {
        coroutineScope.launch { addContestantUseCase.insertContestant(contestantName) }
    }

    override fun onAddContestantStarted() {
        view.changeAddButtonState()
    }

    override fun onAddContestantsCompleted() {
        dismiss()
        eventPoster.postEvent(ModelModifiedEvent(ModelModifiedEvent.Event.CONTESTANTS_MODIFIED))
    }

    override fun onAddContestantFailed(errorMessage: String) {
        view.revertAddButtonState()
        messagesManager.showAddContestantUseCaseErrorMessage(errorMessage)
    }

    override fun onAddContestantUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            OperationResult.OnStarted -> onAddContestantStarted()
            is OperationResult.OnCompleted -> onAddContestantsCompleted()
            is OperationResult.OnError -> onAddContestantFailed(result.errorMessage!!)
        }
    }

    override fun onStart() {
        view.addListener(this)
        addContestantUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addContestantUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}