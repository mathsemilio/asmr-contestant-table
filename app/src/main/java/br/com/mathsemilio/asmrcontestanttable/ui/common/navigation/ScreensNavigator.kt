package br.com.mathsemilio.asmrcontestanttable.ui.common.navigation

import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destinations.TopDestination
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableFragment
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsFragment

class ScreensNavigator(
    private val fragmentManager: FragmentManager,
    private val navigationEventListener: NavigationEventListener
) {
    fun toContestantsTableScreen() {
        fragmentManager.replaceFragmentAtContainerWith(ContestantsTableFragment())
        navigationEventListener.onNavigateToTopDestination(TopDestination.CONTESTANTS_TABLE)
    }

    fun toWeekHighlightsScreen() {
        fragmentManager.replaceFragmentAtContainerWith(WeekHighlightsFragment())
        navigationEventListener.onNavigateToTopDestination(TopDestination.WEEK_HIGHLIGHTS)
    }
}