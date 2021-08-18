package br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.fetch

interface FetchContestantsUseCase {
    suspend fun fetchContestants()
}