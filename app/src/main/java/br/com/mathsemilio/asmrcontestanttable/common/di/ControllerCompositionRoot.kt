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

import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.AddContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.DeleteContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.FetchContestantsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.UpdateContestantUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.AddWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.FetchWeekHighlightsUseCase
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.DialogManager
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.MessagesManager

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val supportFragmentManager get() = activityCompositionRoot.supportFragmentManager

    private val applicationContext get() = activityCompositionRoot.applicationContext

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPublisher get() = activityCompositionRoot.eventPublisher

    val eventSubscriber get() = activityCompositionRoot.eventSubscriber

    val viewFactory get() = activityCompositionRoot.viewFactory

    val dialogManager by lazy {
        DialogManager(supportFragmentManager, applicationContext)
    }

    val messagesManager by lazy {
        MessagesManager(applicationContext)
    }

    val addContestantUseCase by lazy {
        AddContestantUseCase(activityCompositionRoot.contestantsEndpoint)
    }

    val addWeekHighlightsUseCase by lazy {
        AddWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)
    }

    val updateContestantUseCase by lazy {
        UpdateContestantUseCase(activityCompositionRoot.contestantsEndpoint)
    }

    val fetchContestantsUseCase by lazy {
        FetchContestantsUseCase(activityCompositionRoot.contestantsEndpoint)
    }

    val fetchWeekHighlightsUseCase by lazy {
        FetchWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)
    }

    val deleteContestantsUseCase by lazy {
        DeleteContestantsUseCase(activityCompositionRoot.contestantsEndpoint)
    }
}