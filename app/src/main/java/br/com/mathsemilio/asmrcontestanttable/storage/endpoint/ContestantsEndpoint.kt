package br.com.mathsemilio.asmrcontestanttable.storage.endpoint

import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider
import br.com.mathsemilio.asmrcontestanttable.data.dao.ContestantDAO
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import kotlinx.coroutines.withContext

class ContestantsEndpoint(
    private val contestantsDAO: ContestantDAO,
    private val weekHighlightsDAO: WeekHighlightsDAO,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun insertContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.insertData(contestant)
                return@withContext Result.Completed(data = null)
            } catch (e: Exception) {
                return@withContext Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun updateContestant(contestant: ASMRContestant): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.updateData(contestant)
                return@withContext Result.Completed(data = null)
            } catch (e: Exception) {
                return@withContext Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun getAllContestants(): Result<List<ASMRContestant>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                return@withContext Result.Completed(data = contestantsDAO.getAllContestants())
            } catch (e: Exception) {
                return@withContext Result.Failed(errorMessage = e.message!!)
            }
        }
    }

    suspend fun deleteAllContestants(): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                contestantsDAO.deleteAllContestants()
                weekHighlightsDAO.deleteAllWeekHighlights()
                return@withContext Result.Completed(data = null)
            } catch (e: Exception) {
                return@withContext Result.Failed(errorMessage = e.message!!)
            }
        }
    }
}