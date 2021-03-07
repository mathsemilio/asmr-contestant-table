package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.ui.NavDestination
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.NavigationEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableScreen
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsListScreen

class ScreensNavigator(
    private val eventPoster: EventPoster,
    private val fragmentManager: FragmentManager,
    private val fragmentContainerManager: FragmentContainerManager
) {
    fun navigateToContestantsTableScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerManager.fragmentContainer.id, ContestantsTableScreen())
            eventPoster.postEvent(NavigationEvent(NavDestination.CONTESTANTS_TABLE))
            commitNow()
        }
    }

    fun navigateToWeekHighlightsScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerManager.fragmentContainer.id, WeekHighlightsListScreen())
            eventPoster.postEvent(NavigationEvent(NavDestination.WEEK_HIGHLIGHTS))
            commitNow()
        }
    }
}