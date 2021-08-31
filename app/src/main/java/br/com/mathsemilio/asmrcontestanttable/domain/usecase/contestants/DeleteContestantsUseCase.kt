package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class DeleteContestantsUseCase(
    private val endpoint: ContestantsEndpoint?
) : BaseObservable<DeleteContestantsUseCase.Listener>() {

    interface Listener {
        fun onAllContestantsDeletedSuccessfully()

        fun onDeleteAllContestantsFailed()
    }

    suspend fun deleteAllContestants() {
        endpoint?.deleteAllContestants().also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    listener.onAllContestantsDeletedSuccessfully()
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onDeleteAllContestantsFailed()
                }
            }
        }
    }
}