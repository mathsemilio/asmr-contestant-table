package br.com.mathsemilio.asmrcontestanttable.ui.common.event

import br.com.mathsemilio.asmrcontestanttable.ui.NavDestination

sealed class NavigationEvent(val destination: NavDestination) {
    class Navigate(destination: NavDestination) : NavigationEvent(destination)
}