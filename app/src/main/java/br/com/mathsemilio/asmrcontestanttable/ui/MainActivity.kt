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
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.NavigationEventListener
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destinations.TopDestination
import br.com.mathsemilio.asmrcontestanttable.ui.common.others.BottomNavigationItem

class MainActivity : BaseActivity(),
    MainActivityView.Listener,
    FragmentContainerManager,
    NavigationEventListener {

    private lateinit var view: MainActivityView

    private lateinit var screensNavigator: ScreensNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = compositionRoot.viewFactory.getMainActivityView(null)

        screensNavigator = compositionRoot.screensNavigator

        setContentView(view.rootView)

        setSupportActionBar(view.toolbar)

        screensNavigator.toContestantsTableScreen()
    }

    override fun onBottomNavigationItemClicked(item: BottomNavigationItem) {
        when (item) {
            BottomNavigationItem.CONTESTANTS_TABLE -> screensNavigator.toContestantsTableScreen()
            BottomNavigationItem.WEEK_HIGHLIGHTS -> screensNavigator.toWeekHighlightsScreen()
        }
    }

    override val fragmentContainer get() = view.fragmentContainer

    override fun onNavigateToTopDestination(destination: TopDestination) {
        view.setToolbarTitleForTopDestination(destination)
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