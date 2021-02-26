package br.com.mathsemilio.asmrcontestanttable.common.observable

interface Observable<T> {
    fun addListener(listener: T)
    fun removeListener(listener: T)
}