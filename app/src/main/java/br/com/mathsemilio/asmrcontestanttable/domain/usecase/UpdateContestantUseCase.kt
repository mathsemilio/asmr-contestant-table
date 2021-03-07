package br.com.mathsemilio.asmrcontestanttable.domain.usecase

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.data.repository.ContestantsRepository
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.OperationResult
import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider
import kotlinx.coroutines.withContext

class UpdateContestantUseCase(
    private val contestantsRepository: ContestantsRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<UpdateContestantUseCase.Listener>() {

    interface Listener {
        fun onUpdateContestantsUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun updateContestantTimesSlept(contestant: ASMRContestant) {
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val previousContestantOverallScore = contestant.score
                val previousContestantTimesSlept = contestant.timesSlept
                contestantsRepository.updateData(
                    contestant.copy(
                        score = previousContestantOverallScore.plus(3),
                        timesSlept = previousContestantTimesSlept.inc()
                    )
                )
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    suspend fun updateContestantTimesDidNotSlept(contestant: ASMRContestant) {
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val previousContestantTimesNotSlept = contestant.timesDidNotSlept
                contestantsRepository.updateData(
                    contestant.copy(timesDidNotSlept = previousContestantTimesNotSlept.inc())
                )
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    suspend fun updateContestantTimesFeltTired(contestant: ASMRContestant) {
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val previousContestantOverallScore = contestant.score
                val previousContestantTimesFeltTired = contestant.timesFeltTired
                contestantsRepository.updateData(
                    contestant.copy(
                        score = previousContestantOverallScore.inc(),
                        timesFeltTired = previousContestantTimesFeltTired.inc()
                    )
                )
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdatedSuccessfully()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onContestantUpdateFailed(e.message!!)
                }
            }
        }
    }

    private fun onContestantUpdatedSuccessfully() {
        listeners.forEach { it.onUpdateContestantsUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onContestantUpdateFailed(errorMessage: String) {
        listeners.forEach { it.onUpdateContestantsUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}