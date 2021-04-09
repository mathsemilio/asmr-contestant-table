/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
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