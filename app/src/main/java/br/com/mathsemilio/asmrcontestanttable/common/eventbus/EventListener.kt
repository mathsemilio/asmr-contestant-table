package br.com.mathsemilio.asmrcontestanttable.common.eventbus

interface EventListener {
    fun onEvent(event: Any)
}