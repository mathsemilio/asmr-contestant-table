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

package br.com.mathsemilio.asmrcontestanttable.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.delegate.FragmentContainerDelegate
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentTransactionManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.NavigationEventListener
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val database
        get() = AppDatabase.getDatabase(activity)

    private val contestantsDao
        get() = database.contestantDAO

    private val weekHighlightsDAO
        get() = database.weekHighlightsDAO

    private val fragmentTransactionManager
        get() = FragmentTransactionManager(
            supportFragmentManager,
            activity as FragmentContainerDelegate
        )

    val screensNavigator by lazy {
        ScreensNavigator(
            fragmentTransactionManager,
            activity as NavigationEventListener
        )
    }

    val viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }

    val permissionsHelper by lazy {
        PermissionsHelper(activity)
    }

    val application
        get() = compositionRoot.application

    val coroutineScopeProvider
        get() = compositionRoot.coroutineScopeProvider

    val contestantsEndpoint
        get() = ContestantsEndpoint(contestantsDao, weekHighlightsDAO)

    val eventPublisher
        get() = compositionRoot.eventPublisher

    val eventSubscriber
        get() = compositionRoot.eventSubscriber

    val supportFragmentManager
        get() = activity.supportFragmentManager

    val weekHighlightsEndpoint
        get() = WeekHighlightsEndpoint(weekHighlightsDAO)
}
