/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

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
    suspend fun fetchWeekHighlights(): List<WeekHighlights>

    @Query("DELETE FROM week_highlights_table")
    suspend fun deleteAllWeekHighlights()
}
