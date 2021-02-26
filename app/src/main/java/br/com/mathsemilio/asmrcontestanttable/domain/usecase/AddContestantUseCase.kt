package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddContestantUseCase(private val contestantsRepository: ContestantsRepository) :
    BaseObservable<EventObserver<OperationResult<Nothing>>>() {

    suspend fun insertContestant(contestantName: String) {
        onAddContestantStarted()
        withContext(Dispatchers.IO) {
            try {
                contestantsRepository.insertData(ASMRContestant(0, contestantName))
                withContext(Dispatchers.Main.immediate) {
                    onContestantAddedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onAddContestantError(e.message!!)
                }
            }
        }
    }

    private fun onAddContestantStarted() {
        listeners.forEach { it.onEvent(OperationResult.OnStarted) }
    }

    private fun onContestantAddedSuccessfully() {
        listeners.forEach { it.onEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAddContestantError(errorMessage: String) {
        listeners.forEach { it.onEvent(OperationResult.OnError(errorMessage)) }
    }
}