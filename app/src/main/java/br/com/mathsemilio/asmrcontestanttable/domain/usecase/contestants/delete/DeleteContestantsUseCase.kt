package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.delete

interface DeleteContestantsUseCase {
    suspend fun deleteAllContestants()
}