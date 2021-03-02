package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider
import kotlinx.coroutines.withContext

class DeleteContestantsUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val weekHighlightsRepository: WeekHighlightsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<DeleteContestantsUseCase.Listener>() {

    interface Listener {
        fun onDeleteContestantsUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun deleteAllContestants() {
        withContext(dispatcherProvider.background) {
            try {
                contestantsRepository.deleteAllContestants()
                weekHighlightsRepository.deleteAllWeekHighlights()
                withContext(dispatcherProvider.main) {
                    onAllContestantsDeletedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) {
                    onAllContestantsDeleteFailed(e.message!!)
                }
            }
        }
    }

    private fun onAllContestantsDeletedSuccessfully() {
        listeners.forEach { it.onDeleteContestantsUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAllContestantsDeleteFailed(errorMessage: String) {
        listeners.forEach { it.onDeleteContestantsUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}