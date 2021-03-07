package br.com.mathsemilio.asmrcontestanttable.common.provider

import kotlinx.coroutines.CoroutineScope

object CoroutineScopeProvider {
    val UIBoundScope get() = CoroutineScope(DispatcherProvider.MAIN)
}