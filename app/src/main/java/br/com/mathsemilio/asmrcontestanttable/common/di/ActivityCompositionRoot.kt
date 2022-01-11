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
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.ui.common.delegate.FragmentContainerDelegate
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentTransactionManager

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val database
        get() = AppDatabase.getDatabase(application)

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

    val viewFactory by lazy { ViewFactory(activity.layoutInflater) }

    val application
        get() = compositionRoot.application

    val contestantsEndpoint
        get() = ContestantsEndpoint(database.contestantDAO, database.weekHighlightsDAO)

    val eventPublisher
        get() = compositionRoot.eventPublisher

    val eventSubscriber
        get() = compositionRoot.eventSubscriber

    val supportFragmentManager
        get() = activity.supportFragmentManager

    val weekHighlightsEndpoint
        get() = WeekHighlightsEndpoint(database.weekHighlightsDAO)
}
