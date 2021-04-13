package br.com.mathsemilio.asmrcontestanttable.ui.common.navigation

import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destinations.TopDestination

interface NavigationEventListener {
    fun onNavigateToTopDestination(destination: TopDestination)
}