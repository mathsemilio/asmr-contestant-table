package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class DeleteContestantsUseCase(private val contestantsEndpoint: ContestantsEndpoint) :
    BaseObservable<DeleteContestantsUseCase.Listener>() {

    interface Listener {
        fun onContestantsDeletedSuccessfully()
        fun onContestantsDeleteFailed(errorMessage: String)
    }

    suspend fun deleteAllContestants() {
        contestantsEndpoint.deleteAllContestants().also { result ->
            when (result) {
                is Result.Completed -> {
                    listeners.forEach { it.onContestantsDeletedSuccessfully() }
                }
                is Result.Failed ->
                    listeners.forEach { it.onContestantsDeleteFailed(result.errorMessage!!) }
            }
        }
    }
}