package br.com.mathsemilio.asmrcontestanttable.common.eventbus

class EventPublisher(private val eventBus: EventBus) {

    fun publish(event: Any) = eventBus.postEvent(event)
}