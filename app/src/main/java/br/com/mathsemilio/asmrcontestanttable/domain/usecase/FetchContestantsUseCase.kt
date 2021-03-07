package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider
import kotlinx.coroutines.withContext

class FetchContestantsUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<FetchContestantsUseCase.Listener>() {

    interface Listener {
        fun onFetchContestantsUseCaseEvent(result: OperationResult<List<ASMRContestant>>)
    }

    suspend fun fetchContestants() {
        onFetchContestantsStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val contestants = contestantsRepository.getAllContestants()
                withContext(dispatcherProvider.MAIN) {
                    onFetchContestantsCompleted(contestants)
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onFetchContestantsFailed(e.message!!)
                }
            }
        }
    }

    private fun onFetchContestantsStarted() {
        listeners.forEach { it.onFetchContestantsUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onFetchContestantsCompleted(contestants: List<ASMRContestant>) {
        listeners.forEach { it.onFetchContestantsUseCaseEvent(OperationResult.OnCompleted(contestants)) }
    }

    private fun onFetchContestantsFailed(errorMessage: String) {
        listeners.forEach { it.onFetchContestantsUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}