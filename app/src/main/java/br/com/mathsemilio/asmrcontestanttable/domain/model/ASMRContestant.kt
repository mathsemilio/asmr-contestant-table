package br.com.mathsemilio.asmrcontestanttable.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.asmrcontestanttable.common.CONTESTANT_TABLE
import java.io.Serializable

@Entity(tableName = CONTESTANT_TABLE)
data class ASMRContestant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val score: Int = 0,
    val timesSlept: Int = 0,
    val timesDidNotSlept: Int = 0,
    val timesFeltTired: Int = 0
) : Serializable