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

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.asmrcontestanttable.storage.database.AppDatabase
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.ContestantsEndpoint
import br.com.mathsemilio.asmrcontestanttable.storage.endpoint.WeekHighlightsEndpoint
import br.com.mathsemilio.asmrcontestanttable.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentContainerManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.FragmentTransactionManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.NavigationEventListener
import br.com.mathsemilio.asmrcontestanttable.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val appCompatActivity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val database get() = AppDatabase.getDatabase(appCompatActivity)

    private val contestantsDao by lazy {
        database.contestantDAO
    }

    private val weekHighlightsDAO by lazy {
        database.weekHighlightsDAO
    }

    private val _contestantsEndpoint by lazy {
        ContestantsEndpoint(contestantsDao, weekHighlightsDAO)
    }

    private val _weekHighlightsEndpoint by lazy {
        WeekHighlightsEndpoint(weekHighlightsDAO)
    }

    private val _fragmentTransactionManager by lazy {
        FragmentTransactionManager(supportFragmentManager, appCompatActivity as FragmentContainerManager)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(_fragmentTransactionManager, appCompatActivity as NavigationEventListener)
    }

    val viewFactory by lazy {
        ViewFactory(appCompatActivity.layoutInflater)
    }

    val permissionsHelper by lazy {
        PermissionsHelper(appCompatActivity)
    }

    val applicationContext: Context get() = appCompatActivity.applicationContext

    val contestantsEndpoint get() = _contestantsEndpoint

    val coroutineScopeProvider get() = compositionRoot.coroutineScopeProvider

    val eventPublisher get() = compositionRoot.eventPublisher

    val eventSubscriber get() = compositionRoot.eventSubscriber

    val screensNavigator get() = _screensNavigator

    val supportFragmentManager get() = appCompatActivity.supportFragmentManager

    val weekHighlightsEndpoint get() = _weekHighlightsEndpoint
}