package br.com.mathsemilio.asmrcontestanttable.data.repository

import br.com.mathsemilio.asmrcontestanttable.data.common.BaseModelRepository
import br.com.mathsemilio.asmrcontestanttable.data.dao.ContestantDAO
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsRepository(contestantDAO: ContestantDAO) :
    BaseModelRepository<ASMRContestant, ContestantDAO>() {

    init {
        dao = contestantDAO
    }

    suspend fun getAllContestants() = dao.getAllContestants()

    suspend fun deleteAllContestants() = dao.deleteAllContestants()
}