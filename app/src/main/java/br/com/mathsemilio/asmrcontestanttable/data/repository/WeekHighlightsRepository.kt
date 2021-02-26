package br.com.mathsemilio.asmrcontestanttable.data.repository

import br.com.mathsemilio.asmrcontestanttable.data.common.BaseModelRepository
import br.com.mathsemilio.asmrcontestanttable.data.dao.WeekHighlightsDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights

class WeekHighlightsRepository(weekHighlightsDAO: WeekHighlightsDAO) :
    BaseModelRepository<WeekHighlights, WeekHighlightsDAO>() {

    init {
        dao = weekHighlightsDAO
    }

    suspend fun getAllWeekHighlights() = dao.getAllWeekHighlights()

    suspend fun deleteAllWeekHighlights() = dao.deleteAllWeekHighlights()
}