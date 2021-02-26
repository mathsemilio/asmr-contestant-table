package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.data.repository.WeekHighlightsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteContestantsUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val weekHighlightsRepository: WeekHighlightsRepository
) : BaseObservable<EventObserver<OperationResult<Nothing>>>() {

    suspend fun deleteAllContestants() {
        withContext(Dispatchers.IO) {
            try {
                contestantsRepository.deleteAllContestants()
                weekHighlightsRepository.deleteAllWeekHighlights()
                withContext(Dispatchers.Main.immediate) {
                    onAllContestantsDeletedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onAllContestantsDeleteFailed(e.message!!)
                }
            }
        }
    }

    private fun onAllContestantsDeletedSuccessfully() {
        listeners.forEach { it.onEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAllContestantsDeleteFailed(errorMessage: String) {
        listeners.forEach { it.onEvent(OperationResult.OnError()) }
    }
}