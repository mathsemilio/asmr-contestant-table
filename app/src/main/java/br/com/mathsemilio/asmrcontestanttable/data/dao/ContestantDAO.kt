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
    suspend fun getAllContestants(): List<ASMRContestant>

    @Query("DELETE FROM asmr_contestant_table")
    suspend fun deleteAllContestants()
}