package br.com.mathsemilio.asmrcontestanttable.common.di

import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventBus
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventPublisher
import br.com.mathsemilio.asmrcontestanttable.common.eventbus.EventSubscriber
import br.com.mathsemilio.asmrcontestanttable.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.common.provider.DispatcherProvider

class CompositionRoot {

    val coroutineScopeProvider get() = CoroutineScopeProvider

    val dispatcherProvider get() = DispatcherProvider

    private val eventBus by lazy { EventBus() }

    private val _eventPublisher by lazy {
        EventPublisher(eventBus)
    }

    private val _eventSubscriber by lazy {
        EventSubscriber(eventBus)
    }

    val eventPublisher get() = _eventPublisher

    val eventSubscriber get() = _eventSubscriber
}