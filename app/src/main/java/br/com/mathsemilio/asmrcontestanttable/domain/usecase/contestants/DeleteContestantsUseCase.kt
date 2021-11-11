package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants

import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint

open class DeleteContestantsUseCase(private val endpoint: ContestantsEndpoint?) {

    sealed class DeleteContestantsResult {
        object Completed : DeleteContestantsResult()
        object Failed : DeleteContestantsResult()
    }

    suspend fun deleteAllContestants(): DeleteContestantsResult {
        var deleteContestantsResult: DeleteContestantsResult

        endpoint?.deleteAllContestants().also { result ->
            deleteContestantsResult = when (result) {
                is Result.Completed -> DeleteContestantsResult.Completed
                is Result.Failed -> DeleteContestantsResult.Failed
                null -> DeleteContestantsResult.Failed
            }
        }

        return deleteContestantsResult
    }
}
