package br.com.mathsemilio.asmrcontestanttable.ui.common.event

class ToolbarEvent(private val _toolbarEvent: Event) {

    enum class Event { RESET_CONTEST_ACTION_CLICKED }

    val toolbarEvent get() = _toolbarEvent
}