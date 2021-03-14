package br.com.mathsemilio.asmrcontestanttable.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.NavigationEvent
import br.com.mathsemilio.asmrcontestanttable.ui.common.event.ToolbarActionEvent
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
        eventPoster.postEvent(ToolbarActionEvent.OnActionClicked(ToolbarAction.RESET_CONTEST))
    }

    override fun getFragmentContainer(): FrameLayout {
        return view.fragmentContainer
    }

    override fun onEvent(event: Any) {
        when (event) {
            is NavigationEvent -> onNavigationEvent(event)
        }
    }

    private fun onNavigationEvent(navigationEvent: NavigationEvent) {
        view.setToolbarTitleBasedOnDestination(navigationEvent.destination)
        view.setToolbarMenuBasedOnDestination(navigationEvent.destination)
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