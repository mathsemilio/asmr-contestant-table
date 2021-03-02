package br.com.mathsemilio.asmrcontestanttable.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableScreen
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsListScreen

class ScreensNavigator(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerManager: FragmentContainerManager
) {
    fun navigateToContestantsTableScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerManager.getFragmentContainer().id, ContestantsTableScreen())
            commitNow()
        }
    }

    fun navigateToWeekHighlightsScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerManager.getFragmentContainer().id, WeekHighlightsListScreen())
            commitNow()
        }
    }
}