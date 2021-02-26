package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider
import kotlinx.coroutines.withContext

class FetchContestantsUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<EventObserver<OperationResult<List<ASMRContestant>>>>() {

    suspend fun getAllContestants() {
        onFetchContestantsStarted()
        withContext(dispatcherProvider.background) {
            try {
                val contestants = contestantsRepository.getAllContestants()
                withContext(dispatcherProvider.main) {
                    onFetchContestantsCompleted(contestants)
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) {
                    onFetchContestantsFailed(e.message!!)
                }
            }
        }
    }

    private fun onFetchContestantsStarted() {
        listeners.forEach { it.onEvent(OperationResult.OnStarted) }
    }

    private fun onFetchContestantsCompleted(contestants: List<ASMRContestant>) {
        listeners.forEach { it.onEvent(OperationResult.OnCompleted(contestants)) }
    }

    private fun onFetchContestantsFailed(errorMessage: String) {
        listeners.forEach { it.onEvent(OperationResult.OnError(errorMessage)) }
    }
}