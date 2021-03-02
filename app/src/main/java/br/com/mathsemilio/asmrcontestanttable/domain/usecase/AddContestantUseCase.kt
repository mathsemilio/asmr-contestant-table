package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider
import kotlinx.coroutines.withContext

class AddContestantUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<AddContestantUseCase.Listener>() {

    interface Listener {
        fun onAddContestantUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun insertContestant(contestantName: String) {
        onAddContestantStarted()
        withContext(dispatcherProvider.background) {
            try {
                contestantsRepository.insertData(ASMRContestant(0, contestantName))
                withContext(dispatcherProvider.main) {
                    onContestantAddedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) {
                    onAddContestantError(e.message!!)
                }
            }
        }
    }

    private fun onAddContestantStarted() {
        listeners.forEach { it.onAddContestantUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onContestantAddedSuccessfully() {
        listeners.forEach { it.onAddContestantUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAddContestantError(errorMessage: String) {
        listeners.forEach { it.onAddContestantUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}