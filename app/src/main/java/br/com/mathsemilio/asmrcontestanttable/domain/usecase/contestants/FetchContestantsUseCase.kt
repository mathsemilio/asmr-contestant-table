package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

class FetchContestantsUseCase(private val contestantsEndpoint: ContestantsEndpoint) :
    BaseObservable<FetchContestantsUseCase.Listener>() {

    interface Listener {
        fun onContestantsFetchedSuccessfully(contestants: List<ASMRContestant>)
        fun onContestantsFetchFailed()
    }

    suspend fun fetchContestants() {
        contestantsEndpoint.getAllContestants().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onContestantsFetchedSuccessfully(result.data!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onContestantsFetchFailed()
                }
            }
        }
    }
}