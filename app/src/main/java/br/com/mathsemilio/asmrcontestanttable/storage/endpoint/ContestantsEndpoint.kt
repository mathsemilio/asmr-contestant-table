package br.com.mathsemilio.asmrcontestanttable.storage.endpoint

import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider
import br.com.mathsemilio.asmrcontestanttable.data.dao.ContestantDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import kotlinx.coroutines.withContext

class ContestantsEndpoint(
    private val contestantsDAO: ContestantDAO,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun insertContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.insertData(contestant)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun updateContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.updateData(contestant)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun getAllContestants(): Result<List<ASMRContestant>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                Result.Completed(data = contestantsDAO.getAllContestants())
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun deleteAllContestants(): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.deleteAllContestants()
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(errorMessage = e.message!!)
            }
        }
    }
}