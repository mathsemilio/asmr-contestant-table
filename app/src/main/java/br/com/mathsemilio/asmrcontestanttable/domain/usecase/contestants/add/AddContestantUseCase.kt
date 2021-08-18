package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.add

interface AddContestantUseCase {
    suspend fun addContestant(contestantName: String)
}