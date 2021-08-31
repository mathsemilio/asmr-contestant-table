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
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.dialogmanager.DialogManagerImpl
import br.com.mathsemilio.asmrcontestanttable.ui.common.manager.messagesmanager.MessagesManagerImpl
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addcontestant.controller.AddContestantBottomSheetController
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.addweekhighlights.controller.AddWeekHighlightsBottomSheetController
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.controller.ContestantsDetailsBottomSheetController
import br.com.mathsemilio.asmrcontestanttable.ui.dialog.promptdialog.controller.PromptDialogController
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.controller.ContestantsTableController
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.controller.WeekHighlightsController

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val supportFragmentManager get() = activityCompositionRoot.supportFragmentManager

    private val applicationContext get() = activityCompositionRoot.applicationContext

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPublisher get() = activityCompositionRoot.eventPublisher

    val eventSubscriber get() = activityCompositionRoot.eventSubscriber

    val viewFactory get() = activityCompositionRoot.viewFactory

    val dialogManager by lazy {
        DialogManagerImpl(supportFragmentManager, applicationContext)
    }

    val messagesManager by lazy {
        MessagesManagerImpl(applicationContext)
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

    val contestantsTableController
        get() = ContestantsTableController(
            eventSubscriber,
            messagesManager,
            dialogManager,
            coroutineScopeProvider.UIBoundScope,
            fetchContestantsUseCase,
            deleteContestantsUseCase
        )

    val weekHighlightsController
        get() = WeekHighlightsController(
            messagesManager,
            eventSubscriber,
            dialogManager,
            coroutineScopeProvider.UIBoundScope,
            fetchWeekHighlightsUseCase
        )

    val addContestantBottomSheetController
        get() = AddContestantBottomSheetController(
            messagesManager,
            coroutineScopeProvider.UIBoundScope,
            eventPublisher,
            addContestantUseCase
        )

    val addWeekHighlightsBottomSheetController
        get() = AddWeekHighlightsBottomSheetController(
            messagesManager,
            eventPublisher,
            coroutineScopeProvider.UIBoundScope,
            addWeekHighlightsUseCase
        )

    val contestantDetailsBottomSheetController
        get() = ContestantsDetailsBottomSheetController(
            messagesManager,
            eventPublisher,
            coroutineScopeProvider.UIBoundScope,
            updateContestantUseCase
        )

    val promptDialogController
        get() = PromptDialogController(eventPublisher)
}