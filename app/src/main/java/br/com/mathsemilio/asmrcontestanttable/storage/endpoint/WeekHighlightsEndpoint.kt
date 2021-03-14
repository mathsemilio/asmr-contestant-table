package br.com.mathsemilio.asmrcontestanttable.storage.endpoint

import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.Result
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import kotlinx.coroutines.withContext

class WeekHighlightsEndpoint(
    private val weekHighlightsDAO: WeekHighlightsDAO,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun insertWeekHighlights(weekHighlights: WeekHighlights): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                weekHighlightsDAO.insertData(weekHighlights)
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = null)
                }
            } catch (e: Exception) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(e.message!!)
                }
            }
        }
    }

    suspend fun getAllWeekHighlights(): Result<List<WeekHighlights>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = weekHighlightsDAO.getAllWeekHighlights())
                }
            } catch (e: Exception) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(e.message!!)
                }
            }
        }
    }

    suspend fun getWeekNumber(): Result<Int> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                return@withContext Result.Completed(
                    data = weekHighlightsDAO.getAllWeekHighlights().size.plus(1)
                )
            } catch (e: Exception) {
                Result.Failed(e.message!!)
            }
        }
    }
}