package br.com.mathsemilio.asmrcontestanttable.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

@Dao
interface WeekHighlightsDAO : BaseDAO<WeekHighlights> {

    @Insert
    override suspend fun insertData(data: WeekHighlights)

    @Query("SELECT * FROM week_highlights_table ORDER BY weekNumber DESC")
    suspend fun getAllWeekHighlights(): List<WeekHighlights>

    @Query("DELETE FROM week_highlights_table")
    suspend fun deleteAllWeekHighlights()
}