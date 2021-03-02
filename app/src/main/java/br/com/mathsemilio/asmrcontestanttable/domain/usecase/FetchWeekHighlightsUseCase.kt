package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider
import kotlinx.coroutines.withContext

class FetchWeekHighlightsUseCase(
    private val weekHighlightsRepository: WeekHighlightsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<FetchWeekHighlightsUseCase.Listener>() {

    interface Listener {
        fun onFetchWeekHighlightsUseCaseEvent(result: OperationResult<List<WeekHighlights>>)
    }

    suspend fun fetchWeekHighlights() {
        onFetchWeekHighlightsStarted()
        withContext(dispatcherProvider.background) {
            try {
                val weekHighlights = weekHighlightsRepository.getAllWeekHighlights()
                withContext(dispatcherProvider.main) {
                    onFetchWeekHighlightsCompleted(weekHighlights)
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) {
                    onFetchWeekHighlightsFailed(e.message!!)
                }
            }
        }
    }

    private fun onFetchWeekHighlightsStarted() {
        listeners.forEach { it.onFetchWeekHighlightsUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onFetchWeekHighlightsCompleted(weekHighlights: List<WeekHighlights>) {
        listeners.forEach {
            it.onFetchWeekHighlightsUseCaseEvent(OperationResult.OnCompleted(weekHighlights))
        }
    }

    private fun onFetchWeekHighlightsFailed(errorMessage: String) {
        listeners.forEach {
            it.onFetchWeekHighlightsUseCaseEvent(OperationResult.OnError(errorMessage))
        }
    }
}