package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ModelModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddWeekHighlightsBottomSheet : BaseBottomSheetDialogFragment(),
    AddWeekHighlightsContract.View.Listener,
    AddWeekHighlightsContract.BottomSheet,
    AddWeekHighlightsUseCase.Listener {

    private lateinit var view: AddWeekHighlightsView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPoster: EventPoster

    private lateinit var addWeekHighlightsUseCase: AddWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        eventPoster = compositionRoot.eventPoster
        addWeekHighlightsUseCase = compositionRoot.addWeekHighlightsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAddWeekHighlightsView(container)
        return view.rootView
    }

    override fun onAddButtonClicked(firstContestantName: String, secondContestantName: String) {
        coroutineScope.launch {
            addWeekHighlightsUseCase.insertContestant(firstContestantName, secondContestantName)
        }
    }

    override fun onAddWeekHighlightsUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            OperationResult.OnStarted -> onAddWeekHighlightsStarted()
            is OperationResult.OnCompleted -> onAddWeekHighlightsCompleted()
            is OperationResult.OnError -> onAddWeekHighlightsFailed(result.errorMessage!!)
        }
    }

    override fun onAddWeekHighlightsStarted() {
        view.changeAddButtonState()
    }

    override fun onAddWeekHighlightsCompleted() {
        dismiss()
        eventPoster.postEvent(
            ModelModifiedEvent(ModelModifiedEvent.Event.WEEK_HIGHLIGHTS_MODIFIED)
        )
    }

    override fun onAddWeekHighlightsFailed(errorMessage: String) {
        view.revertAddButtonState()
        messagesManager.showAddWeekHighlightsUseCaseErrorMessage(errorMessage)
    }

    override fun onStart() {
        view.addListener(this)
        addWeekHighlightsUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        addWeekHighlightsUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}