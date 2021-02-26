package br.com.mathsemilio.asmrcontestanttable.ui

import android.os.Bundle
import android.widget.FrameLayout
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.ScreensNavigator

class MainActivity : BaseActivity(),
    MainActivityContract.View.Listener,
    FragmentContainerHelper {

    private lateinit var view: MainActivityView

    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        screensNavigator = compositionRoot.screensNavigator

        if (savedInstanceState == null)
            screensNavigator.navigateToContestantsTableScreen()

        setContentView(R.layout.activity_main)
    }

    override fun onBottomNavigationItemClicked(destination: NavDestination) {
        when (destination) {
            NavDestination.CONTESTANTS_TABLE -> screensNavigator.navigateToContestantsTableScreen()
            NavDestination.WEEK_HIGHLIGHTS -> screensNavigator.navigateToWeekHighlightsScreen()
        }
        view.setToolbarTitleBasedOnDestination(destination)
        view.setToolbarMenuBasedOnDestination(destination)
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