package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.delete

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class DeleteContestantsUseCaseImpl(private val endpoint: ContestantsEndpoint) :
    BaseObservable<DeleteContestantsUseCaseImpl.Listener>(),
    DeleteContestantsUseCase {

    interface Listener {
        fun onAllContestantsDeletedSuccessfully()

        fun onDeleteAllContestantsFailed()
    }

    override suspend fun deleteAllContestants() {
        endpoint.deleteAllContestants().also { result ->
            when (result) {
                is Result.Completed -> notifyListener { it.onAllContestantsDeletedSuccessfully() }
                is Result.Failed -> notifyListener { it.onDeleteAllContestantsFailed() }
            }
        }
    }
}