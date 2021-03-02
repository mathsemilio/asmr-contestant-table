package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddWeekHighlightsUseCase(
    private val weekHighlightsRepository: WeekHighlightsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<AddWeekHighlightsUseCase.Listener>() {

    interface Listener {
        fun onAddWeekHighlightsUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun insertContestant(firstContestantName: String, secondContestantName: String) {
        onAddWeekHighlightsStarted()
        withContext(dispatcherProvider.background) {
            try {
                launch {
                    val weekNumber = getWeekNumber()
                    weekHighlightsRepository.insertData(
                        WeekHighlights(0, weekNumber, firstContestantName, secondContestantName)
                    )
                }
                withContext(dispatcherProvider.main) {
                    onWeekHighlightsAddedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) {
                    onAddWeekHighlightsError(e.message!!)
                }
            }
        }
    }

    private suspend fun getWeekNumber(): Int {
        return withContext(dispatcherProvider.background) {
            try {
                weekHighlightsRepository.getAllWeekHighlights().size + 1
            } catch (e: Exception) {
                throw RuntimeException(e.message!!)
            }
        }
    }

    private fun onAddWeekHighlightsStarted() {
        listeners.forEach { it.onAddWeekHighlightsUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onWeekHighlightsAddedSuccessfully() {
        listeners.forEach { it.onAddWeekHighlightsUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAddWeekHighlightsError(errorMessage: String) {
        listeners.forEach { it.onAddWeekHighlightsUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}