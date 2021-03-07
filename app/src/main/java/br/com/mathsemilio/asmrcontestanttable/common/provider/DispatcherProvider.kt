package br.com.mathsemilio.asmrcontestanttable.common.provider

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    val MAIN get() = Dispatchers.Main.immediate
    val BACKGROUND get() = Dispatchers.IO
}