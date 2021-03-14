package br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint

class FetchWeekHighlightsUseCase(private val weekHighlightsEndpoint: WeekHighlightsEndpoint) :
    BaseObservable<FetchWeekHighlightsUseCase.Listener>() {

    interface Listener {
        fun onWeekHighlightsFetchedSuccessfully(weekHighlights: List<WeekHighlights>)
        fun onWeekHighlightsFetchFailed(errorMessage: String)
    }

    suspend fun fetchWeekHighlights() {
        weekHighlightsEndpoint.getAllWeekHighlights().also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onWeekHighlightsFetchedSuccessfully(result.data!!) }
                is Result.Failed ->
                    listeners.forEach { it.onWeekHighlightsFetchFailed(result.errorMessage!!) }
            }
        }
    }
}