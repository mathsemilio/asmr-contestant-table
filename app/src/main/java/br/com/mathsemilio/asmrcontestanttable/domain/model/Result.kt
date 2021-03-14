package br.com.mathsemilio.asmrcontestanttable.domain.model

sealed class Result<out T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Completed<T>(data: T?) : Result<T>(data)
    class Failed<T>(errorMessage: String?) : Result<T>(errorMessage = errorMessage)
}