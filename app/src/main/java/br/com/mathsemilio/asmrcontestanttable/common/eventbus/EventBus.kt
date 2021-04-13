package br.com.mathsemilio.asmrcontestanttable.common.eventbus

import br.com.mathsemilio.asmrcontestanttable.common.observable.BaseObservable

class EventBus : BaseObservable<EventListener>() {

    fun postEvent(event: Any) {
        listeners.forEach { listener ->
            listener.onEvent(event)
        }
    }
}