package br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint

class AddWeekHighlightsUseCase(private val weekHighlightsEndpoint: WeekHighlightsEndpoint) :
    BaseObservable<AddWeekHighlightsUseCase.Listener>() {

    interface Listener {
        fun onWeekHighlightsAddedSuccessfully()
        fun onWeekHighlightsAddFailed(errorMessage: String)
    }

    suspend fun insertWeekHighlights(firstContestantName: String, secondContestantName: String) {
        weekHighlightsEndpoint.insertWeekHighlights(
            WeekHighlights(0, getWeekNumber(), firstContestantName, secondContestantName)
        ).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onWeekHighlightsAddedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onWeekHighlightsAddFailed(result.errorMessage!!) }
            }
        }
    }

    private suspend fun getWeekNumber(): Int {
        var weekNumber: Int
        weekHighlightsEndpoint.getWeekNumber().also { result ->
            when (result) {
                is Result.Completed -> weekNumber = result.data!!
                is Result.Failed -> throw RuntimeException(result.errorMessage!!)
            }
        }
        return weekNumber
    }
}