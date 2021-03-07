package br.com.mathsemilio.asmrcontestanttable.ui.common.event

import br.com.mathsemilio.asmrcontestanttable.ui.NavDestination

class NavigationEvent(private val _destination: NavDestination) {

    val destination get() = _destination
}