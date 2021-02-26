
package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WeekHighlightsListScreen : BaseFragment(),
    WeekHighlightsContract.ListScreen.Listener,
    EventObserver<OperationResult<List<WeekHighlights>>> {

    private lateinit var view: WeekHighlightsListScreenView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesHelper: MessagesHelper
    private lateinit var dialogHelper: DialogHelper

    private lateinit var fetchWeekHighlightsUseCase: FetchWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope

        messagesHelper = compositionRoot.messagesHelper

        dialogHelper = compositionRoot.dialogHelper

        fetchWeekHighlightsUseCase = compositionRoot.fetchWeekHighlightsUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getWeekHighlightsListScreenView(container)
        return view.rootView
    }

    private fun getWeekHighlights() {
        coroutineScope.launch { fetchWeekHighlightsUseCase.getAllWeekHighlights() }
    }

    override fun onAddButtonClicked() {
        dialogHelper.showAddWeekHighlightsBottomSheet()
    }

    override fun onEvent(event: OperationResult<List<WeekHighlights>>) {
        when (event) {
            OperationResult.OnStarted ->
                onFetchWeekHighlightsStarted()
            is OperationResult.OnCompleted ->
                onFetchWeekHighlightsCompleted(event.data ?: throw NullPointerException())
            is OperationResult.OnError ->
                onFetchWeekHighlightsError(event.errorMessage!!)
        }
    }

    private fun onFetchWeekHighlightsStarted() {
        view.showProgressIndicator()
    }

    private fun onFetchWeekHighlightsCompleted(weekHighlights: List<WeekHighlights>) {
        view.hideProgressIndicator()
        view.bindWeekHighlights(weekHighlights)
    }

    private fun onFetchWeekHighlightsError(errorMessage: String) {
        view.hideProgressIndicator()
        messagesHelper.showFetchWeekHighlightsUseCaseErrorMessage(errorMessage)
    }

    override fun onStart() {
        view.addListener(this)
        fetchWeekHighlightsUseCase.addListener(this)
        getWeekHighlights()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchWeekHighlightsUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.cancel()
        super.onDestroyView()
    }
}