package br.com.mathsemilio.asmrcontestanttable.common.di

import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.CoroutineScopeProvider
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.DispatcherProvider

class CompositionRoot {

    val coroutineScopeProvider get() = CoroutineScopeProvider

    val dispatcherProvider get() = DispatcherProvider

    private val _eventPoster by lazy { EventPoster() }
    val eventPoster get() = _eventPoster
}