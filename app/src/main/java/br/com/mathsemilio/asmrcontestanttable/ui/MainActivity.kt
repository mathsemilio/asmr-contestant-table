package br.com.mathsemilio.asmrcontestanttable.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ToolbarEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.ScreensNavigator

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerManager {

    private lateinit var view: MainActivityView

    private lateinit var eventPoster: EventPoster
    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        eventPoster = compositionRoot.eventPoster
        screensNavigator = compositionRoot.screensNavigator

        setContentView(view.rootView)

        if (savedInstanceState == null)
            screensNavigator.navigateToContestantsTableScreen()
    }

    override fun onBottomNavigationItemClicked(destination: NavDestination) {
        when (destination) {
            NavDestination.CONTESTANTS_TABLE ->
                screensNavigator.navigateToContestantsTableScreen()
            NavDestination.WEEK_HIGHLIGHTS ->
                screensNavigator.navigateToWeekHighlightsScreen()
        }
    }

    override fun onToolbarActionResetContestClicked() {
        eventPoster.postEvent(ToolbarEvent(ToolbarEvent.Event.RESET_CONTEST_ACTION_CLICKED))
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.fragmentContainer
    }

    override fun onStart() {
        view.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        super.onStop()
    }
}