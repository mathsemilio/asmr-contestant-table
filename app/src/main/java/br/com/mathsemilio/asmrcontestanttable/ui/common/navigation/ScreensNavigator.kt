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

package br.com.mathsemilio.asmrcontestanttable.ui.common.navigation

import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentTransactionManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.destination.Destinations
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.controller.ContestantsTableFragment
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.controller.WeekHighlightsFragment

class ScreensNavigator(
    private val fragmentTransactionManager: FragmentTransactionManager,
    private val navigationEventListener: NavigationEventListener
) {
    fun toContestantsTableScreen() {
        fragmentTransactionManager.replaceFragmentAtContainerWith(
            ContestantsTableFragment.newInstance()
        )

        navigationEventListener.onNavigateTo(Destinations.CONTESTANTS_TABLE)
    }

    fun toWeekHighlightsScreen() {
        fragmentTransactionManager.replaceFragmentAtContainerWith(
            WeekHighlightsFragment.newInstance()
        )

        navigationEventListener.onNavigateTo(Destinations.WEEK_HIGHLIGHTS)
    }
}
