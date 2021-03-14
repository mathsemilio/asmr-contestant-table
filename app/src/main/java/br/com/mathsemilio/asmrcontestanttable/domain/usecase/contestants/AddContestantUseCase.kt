package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class AddContestantUseCase(private val contestantsEndpoint: ContestantsEndpoint) :
    BaseObservable<AddContestantUseCase.Listener>() {

    interface Listener {
        fun onContestantAddedSuccessfully()
        fun onContestantAddFailed(errorMessage: String)
    }

    suspend fun insertContestant(contestantName: String) {
        contestantsEndpoint.insertContestant(ASMRContestant(0, contestantName)).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onContestantAddedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onContestantAddFailed(result.errorMessage!!) }
            }
        }
    }
}