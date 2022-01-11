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

import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.*
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.weekhighlights.*
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.*
import br.com.mathsemilio.asmrcontestanttable.ui.common.controller.ControllerFactory
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManagerImpl
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManagerImpl

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val controllerFactory by lazy { ControllerFactory(this) }

    val dialogManager
        get() = DialogManagerImpl(
            activityCompositionRoot.supportFragmentManager,
            activityCompositionRoot.application
        )

    val eventSubscriber
        get() = activityCompositionRoot.eventSubscriber

    val eventPublisher
        get() = activityCompositionRoot.eventPublisher

    val messagesManager
        get() = MessagesManagerImpl(activityCompositionRoot.application)

    val viewFactory
        get() = activityCompositionRoot.viewFactory

    val addContestantUseCase
        get() = AddContestantUseCase(activityCompositionRoot.contestantsEndpoint)

    val addWeekHighlightsUseCase
        get() = AddWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)

    val deleteContestantsUseCase
        get() = DeleteContestantsUseCase(activityCompositionRoot.contestantsEndpoint)

    val fetchContestantsUseCase
        get() = FetchContestantsUseCase(activityCompositionRoot.contestantsEndpoint)

    val fetchWeekHighlightsUseCase
        get() = FetchWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)

    val updateContestantTimesSleptUseCase
        get() = UpdateContestantTimesSleptUseCase(activityCompositionRoot.contestantsEndpoint)

    val updateContestantTimesFeltTiredUseCase
        get() = UpdateContestantTimesFeltTiredUseCase(activityCompositionRoot.contestantsEndpoint)

    val updateContestantTimesDidNotSleptUseCase
        get() = UpdateContestantTimesDidNotSleptUseCase(activityCompositionRoot.contestantsEndpoint)
}
