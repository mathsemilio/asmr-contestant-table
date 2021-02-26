package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddWeekHighlightsUseCase(private val weekHighlightsRepository: WeekHighlightsRepository) :
    BaseObservable<EventObserver<OperationResult<Nothing>>>() {

    suspend fun insertContestant(firstContestantName: String, secondContestantName: String) {
        onAddWeekHighlightsStarted()
        withContext(Dispatchers.IO) {
            try {
                weekHighlightsRepository.insertData(
                    WeekHighlights(
                        0, getWeekNumber(), firstContestantName, secondContestantName
                    )
                )
                withContext(Dispatchers.Main.immediate) {
                    onWeekHighlightsAddedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onAddWeekHighlightsError(e.message!!)
                }
            }
        }
    }

    private suspend fun getWeekNumber(): Int {
        return withContext(Dispatchers.IO) {
            try {
                weekHighlightsRepository.getAllWeekHighlights().size + 1
            } catch (e: Exception) {
                throw RuntimeException(e.message!!)
            }
        }
    }

    private fun onAddWeekHighlightsStarted() {
        listeners.forEach { it.onEvent(OperationResult.OnStarted) }
    }

    private fun onWeekHighlightsAddedSuccessfully() {
        listeners.forEach { it.onEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAddWeekHighlightsError(errorMessage: String) {
        listeners.forEach { it.onEvent(OperationResult.OnError()) }
    }
}