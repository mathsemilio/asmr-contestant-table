package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.asmrcontestanttable.common.INVALID_EVENT
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseFragment
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ModelModifiedEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.MessagesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class WeekHighlightsListScreen : BaseFragment(),
    WeekHighlightsContract.View.Listener,
    WeekHighlightsContract.Screen,
    FetchWeekHighlightsUseCase.Listener,
    EventPoster.EventListener {

    private lateinit var view: WeekHighlightsViewView

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var messagesManager: MessagesManager
    private lateinit var dialogManager: DialogManager
    private lateinit var eventPoster: EventPoster

    private lateinit var fetchWeekHighlightsUseCase: FetchWeekHighlightsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        messagesManager = compositionRoot.messagesHelper
        dialogManager = compositionRoot.dialogHelper
        eventPoster = compositionRoot.eventPoster
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

    override fun onAddButtonClicked() {
        dialogManager.showAddWeekHighlightsBottomSheet()
    }

    override fun fetchWeekHighlights() {
        coroutineScope.launch { fetchWeekHighlightsUseCase.fetchWeekHighlights() }
    }

    override fun onWeekHighlightsFetchStarted() {
        view.showProgressIndicator()
    }

    override fun onWeekHighlightsFetchCompleted(weekHighlights: List<WeekHighlights>) {
        view.hideProgressIndicator()
        view.bindWeekHighlights(weekHighlights)
    }

    override fun onWeekHighlightsFetchFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showFetchWeekHighlightsUseCaseErrorMessage(errorMessage)
    }

    override fun onWeekHighlightsModified(event: ModelModifiedEvent.Event) {
        when (event) {
            ModelModifiedEvent.Event.WEEK_HIGHLIGHTS_MODIFIED -> fetchWeekHighlights()
            else -> throw IllegalArgumentException(INVALID_EVENT)
        }
    }

    override fun onFetchWeekHighlightsUseCaseEvent(result: OperationResult<List<WeekHighlights>>) {
        when (result) {
            OperationResult.OnStarted ->
                onWeekHighlightsFetchStarted()
            is OperationResult.OnCompleted ->
                onWeekHighlightsFetchCompleted(result.data ?: throw NullPointerException())
            is OperationResult.OnError ->
                onWeekHighlightsFetchFailed(result.errorMessage!!)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ModelModifiedEvent -> onWeekHighlightsModified(event.modelModifiedEvent)
        }
    }

    override fun onStart() {
        view.addListener(this)
        fetchWeekHighlightsUseCase.addListener(this)
        eventPoster.addListener(this)
        fetchWeekHighlights()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchWeekHighlightsUseCase.removeListener(this)
        eventPoster.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}