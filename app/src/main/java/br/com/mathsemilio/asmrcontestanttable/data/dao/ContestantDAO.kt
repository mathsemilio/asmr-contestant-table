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
import androidx.room.Update
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

@Dao
interface ContestantDAO : BaseDAO<ASMRContestant> {

    @Insert
    override suspend fun insertData(data: ASMRContestant)

    @Update
    override suspend fun updateData(data: ASMRContestant)

    @Query("SELECT * FROM asmr_contestant_table ORDER BY score DESC")
    suspend fun fetchContestants(): List<ASMRContestant>

    @Query("DELETE FROM asmr_contestant_table")
    suspend fun deleteAllContestants()
}
