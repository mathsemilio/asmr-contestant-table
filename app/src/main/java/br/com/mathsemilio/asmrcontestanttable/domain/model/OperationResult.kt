package br.com.mathsemilio.asmrcontestanttable.domain.model

sealed class OperationResult<out T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    object OnStarted : OperationResult<Nothing>()
    class OnCompleted<T>(data: T?) : OperationResult<T>(data)
    class OnError(errorMessage: String) : OperationResult<Nothing>(null, errorMessage)
}