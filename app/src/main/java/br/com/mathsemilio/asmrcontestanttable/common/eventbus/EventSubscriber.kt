package br.com.mathsemilio.asmrcontestanttable.common.eventbus

class EventSubscriber(private val eventBus: EventBus) {

    fun subscribe(listener: EventListener) = eventBus.addListener(listener)

    fun unsubscribe(listener: EventListener) = eventBus.removeListener(listener)
}