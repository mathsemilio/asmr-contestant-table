package br.com.mathsemilio.asmrcontestanttable.common.observable

interface EventObserver<Event> {
    fun onEvent(event: Event)
}