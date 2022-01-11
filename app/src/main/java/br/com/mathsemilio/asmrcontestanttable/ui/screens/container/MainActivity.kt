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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.container

import android.os.Bundle
import br.com.mathsemilio.asmrcontestanttable.ui.common.BaseActivity
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem
import br.com.mathsemilio.asmrcontestanttable.ui.screens.container.view.MainActivityView
import br.com.mathsemilio.asmrcontestanttable.ui.common.delegate.FragmentContainerDelegate
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.NavigationEventListener
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destination.Destinations
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destination.NavDestination

class MainActivity : BaseActivity(),
    MainActivityView.Listener,
    FragmentContainerDelegate,
    NavigationEventListener {

    companion object {
        private const val CURRENT_DESTINATION_KEY = "CURRENT_DESTINATION"
    }

    private lateinit var view: MainActivityView

    private lateinit var screensNavigator: ScreensNavigator

    private var destination = Destinations.CONTESTANTS_TABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(parent = null)

        screensNavigator = compositionRoot.screensNavigator

        setContentView(view.rootView)

        setSupportActionBar(view.toolbar)

        if (savedInstanceState == null)
            screensNavigator.toContestantsTableScreen()
        else
            onStateRestored(savedInstanceState)
    }

    override val fragmentContainerId
        get() = view.fragmentContainerId

    private fun onStateRestored(savedInstanceState: Bundle) {
        destination = savedInstanceState.getSerializable(
            CURRENT_DESTINATION_KEY
        ) as NavDestination

        view.setToolbarTitle(destination.titleId)
        view.setBottomNavigationHighlightedItemBasedOn(destination.name)
    }

    override fun onBottomNavigationItemClicked(item: BottomNavigationItem) {
        when (item) {
            BottomNavigationItem.CONTESTANTS_TABLE -> screensNavigator.toContestantsTableScreen()
            BottomNavigationItem.WEEK_HIGHLIGHTS -> screensNavigator.toWeekHighlightsScreen()
        }
    }

    override fun onNavigateTo(destination: NavDestination) {
        this.destination = destination
        view.setToolbarTitle(destination.titleId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(CURRENT_DESTINATION_KEY, destination)
    }

    override fun onStart() {
        super.onStart()
        view.addObserver(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeObserver(this)
    }
}
