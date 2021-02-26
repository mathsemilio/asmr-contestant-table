package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import kotlinx.coroutines.CoroutineScope

object CoroutineScopeProvider {

    /**
     *  Coroutine Scope with its context bound to the Main.immediate dispatcher
     */
    val UIBoundScope get() = CoroutineScope(DispatcherProvider.main)
}