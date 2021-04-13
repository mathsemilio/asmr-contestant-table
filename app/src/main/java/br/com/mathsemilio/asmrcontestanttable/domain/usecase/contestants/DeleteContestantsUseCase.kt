package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class DeleteContestantsUseCase(private val contestantsEndpoint: ContestantsEndpoint) :
    BaseObservable<DeleteContestantsUseCase.Listener>() {

    interface Listener {
        fun onAllContestantsDeletedSuccessfully()
        fun onDeleteAllContestantsFailed()
    }

    suspend fun deleteAllContestants() {
        contestantsEndpoint.deleteAllContestants().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onAllContestantsDeletedSuccessfully()
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onDeleteAllContestantsFailed()
                }
            }
        }
    }
}