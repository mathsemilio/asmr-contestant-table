package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.common.observable.EventObserver
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateContestantUseCase(private val contestantsRepository: ContestantsRepository) :
    BaseObservable<EventObserver<OperationResult<Nothing>>>() {

    suspend fun updateContestantTimesSlept(contestant: ASMRContestant) {
        withContext(Dispatchers.IO) {
            try {
                val previousContestantOverallScore = contestant.score
                val previousContestantTimesSlept = contestant.timesSlept
                contestantsRepository.updateData(
                    contestant.copy(
                        score = previousContestantOverallScore.plus(3),
                        timesSlept = previousContestantTimesSlept.inc()
                    )
                )
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    suspend fun updateContestantTimesDidNotSlept(contestant: ASMRContestant) {
        withContext(Dispatchers.IO) {
            try {
                val previousContestantTimesNotSlept = contestant.timesDidNotSlept
                contestantsRepository.updateData(
                    contestant.copy(timesDidNotSlept = previousContestantTimesNotSlept.inc())
                )
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    suspend fun updateContestantTimesFeltTired(contestant: ASMRContestant) {
        withContext(Dispatchers.IO) {
            try {
                val previousContestantOverallScore = contestant.score
                val previousContestantTimesFeltTired = contestant.timesFeltTired
                contestantsRepository.updateData(
                    contestant.copy(
                        score = previousContestantOverallScore.inc(),
                        timesFeltTired = previousContestantTimesFeltTired.inc()
                    )
                )
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    private fun onContestantUpdatedSuccessfully() {
        listeners.forEach { it.onEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onContestantUpdateFailed(errorMessage: String) {
        listeners.forEach { it.onEvent(OperationResult.OnError()) }
    }
}