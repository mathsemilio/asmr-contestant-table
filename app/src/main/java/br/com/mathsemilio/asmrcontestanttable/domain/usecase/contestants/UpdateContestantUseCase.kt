package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class UpdateContestantUseCase(private val contestantsEndpoint: ContestantsEndpoint) :
    BaseObservable<UpdateContestantUseCase.Listener>() {

    interface Listener {
        fun onContestantUpdatedSuccessfully()
        fun onContestantsUpdateFailed(errorMessage: String)
    }

    suspend fun updateContestantTimesSlept(contestant: ASMRContestant) {
        val previousScore = contestant.score
        val previousTimesSlept = contestant.timesSlept
        contestantsEndpoint.updateContestant(
            contestant.copy(
                score = previousScore.plus(3),
                timesSlept = previousTimesSlept.inc()
            )
        ).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onContestantUpdatedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onContestantsUpdateFailed(result.errorMessage!!) }
            }
        }
    }

    suspend fun updateContestantTimesDidNotSlept(contestant: ASMRContestant) {
        val previousTimesDidNotSlept = contestant.timesDidNotSlept
        contestantsEndpoint.updateContestant(
            contestant.copy(timesDidNotSlept = previousTimesDidNotSlept.inc())
        ).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onContestantUpdatedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onContestantsUpdateFailed(result.errorMessage!!) }
            }
        }
    }

    suspend fun updateContestantTimesFeltTired(contestant: ASMRContestant) {
        val previousScore = contestant.score
        val previousTimesFeltTired = contestant.timesFeltTired
        contestantsEndpoint.updateContestant(
            contestant.copy(
                score = previousScore.inc(),
                timesFeltTired = previousTimesFeltTired.inc()
            )
        ).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onContestantUpdatedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onContestantsUpdateFailed(result.errorMessage!!) }
            }
        }
    }
}