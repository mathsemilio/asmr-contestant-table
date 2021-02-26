package br.com.mathsemilio.asmrcontestanttable.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.asmrcontestanttable.common.WEEK_HIGHLIGHTS_TABLE

@Entity(tableName = WEEK_HIGHLIGHTS_TABLE)
data class WeekHighlights(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weekNumber: Int,
    val firstContestantName: String,
    val secondContestantName: String
)