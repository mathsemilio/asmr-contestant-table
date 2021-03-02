package br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable

class EventPoster : BaseObservable<EventPoster.EventListener>() {

    interface EventListener {
        fun onEvent(event: Any)
    }

    fun postEvent(event: Any) {
        listeners.forEach { it.onEvent(event) }
    }
}