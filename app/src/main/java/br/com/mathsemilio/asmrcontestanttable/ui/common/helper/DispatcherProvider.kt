package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    val main get() = Dispatchers.Main.immediate
    val background get() = Dispatchers.IO
}