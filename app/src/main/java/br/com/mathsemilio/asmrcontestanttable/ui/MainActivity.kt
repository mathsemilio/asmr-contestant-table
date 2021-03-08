package br.com.mathsemilio.asmrcontestanttable.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.NavigationEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ToolbarEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.poster.EventPoster
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.ScreensNavigator

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerManager,
    EventPoster.EventListener {

    private lateinit var view: MainActivityView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var eventPoster: EventPoster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        screensNavigator = compositionRoot.screensNavigator
        eventPoster = compositionRoot.eventPoster

        setContentView(view.rootView)

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

    override val fragmentContainer: FrameLayout
        get() = view.fragmentContainer

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent -> handleNavigationEvent(event.destination)
        }
    }

    private fun handleNavigationEvent(destination: NavDestination) {
        view.setToolbarTitleBasedOnDestination(destination)
        view.setToolbarMenuBasedOnDestination(destination)
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        super.onStop()
    }
}