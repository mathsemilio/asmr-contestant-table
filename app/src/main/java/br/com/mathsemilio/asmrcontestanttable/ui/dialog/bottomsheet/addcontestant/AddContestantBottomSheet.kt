package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.DataModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AddContestantBottomSheet : BaseBottomSheetDialogFragment(),
    AddContestantContract.View.Listener,
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
        view.changeAddButtonState()
        coroutineScope.launch { addContestantUseCase.insertContestant(contestantName) }
    }

    override fun onContestantAddedSuccessfully() {
        dismiss()
        eventPoster.postEvent(DataModifiedEvent.OnDataModified)
    }

    override fun onContestantAddFailed(errorMessage: String) {
        view.revertAddButtonState()
        messagesManager.showAddContestantUseCaseErrorMessage(errorMessage)
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