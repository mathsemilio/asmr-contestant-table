package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.ARG_CONTESTANT
import br.com.mathsemilio.asmrcontestanttable.common.INVALID_OPERATION
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.DataModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ContestantDetailsBottomSheet : BaseBottomSheetDialogFragment(),
    ContestantDetailsContract.View.Listener,
    ContestantDetailsContract.BottomSheet,
    UpdateContestantUseCase.Listener {

    companion object {
        fun newInstance(contestant: ASMRContestant): ContestantDetailsBottomSheet {
            val args = Bundle(1).apply { putSerializable(ARG_CONTESTANT, contestant) }
            val contestantDetailsBottomSheet = ContestantDetailsBottomSheet()
            contestantDetailsBottomSheet.arguments = args
            return contestantDetailsBottomSheet
        }
    }

    private lateinit var view: ContestantDetailsView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var eventPoster: EventPoster

    private lateinit var updateContestantUseCase: UpdateContestantUseCase

    private lateinit var currentContestant: ASMRContestant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        eventPoster = compositionRoot.eventPoster
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
        bindContestant()
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

    override fun getContestant(): ASMRContestant {
        return arguments?.getSerializable(ARG_CONTESTANT) as ASMRContestant
    }

    override fun bindContestant() {
        currentContestant = getContestant()
        view.bindContestantsDetails(currentContestant)
    }

    override fun onUpdateContestantCompleted() {
        dismiss()
        eventPoster.postEvent(DataModifiedEvent.OnDataModified)
    }

    override fun onUpdateContestantFailed(errorMessage: String) {
        messagesManager.showContestantUpdateUseCaseErrorMessage(errorMessage)
    }

    override fun onContestantUpdatedSuccessfully() {
        when (result) {
            is OperationResult.OnCompleted -> onUpdateContestantCompleted()
            is OperationResult.OnError -> onUpdateContestantFailed(result.errorMessage!!)
            else -> throw IllegalArgumentException(INVALID_OPERATION)
        }
    }

    override fun onStart() {
        view.addListener(this)
        updateContestantUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        updateContestantUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}