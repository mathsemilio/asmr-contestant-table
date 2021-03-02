package br.com.mathsemilio.asmrcontestanttable.ui.common.event

class ModelModifiedEvent(private val _modelModifiedEvent: Event) {

    enum class Event { CONTESTANTS_MODIFIED, WEEK_HIGHLIGHTS_MODIFIED }

    val modelModifiedEvent get() = _modelModifiedEvent
}