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
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesDidNotSleptUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesFeltTiredUseCase
import br.com.mathsemilio.asmrcontestanttable.domain.usecase.contestants.update.UpdateContestantTimesSleptUseCase
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

    private val dialogManager
        get() = DialogManagerImpl(
            activityCompositionRoot.supportFragmentManager,
            activityCompositionRoot.application
        )

    private val messagesManager
        get() = MessagesManagerImpl(activityCompositionRoot.application)

    private val addContestantUseCase
        get() = AddContestantUseCase(activityCompositionRoot.contestantsEndpoint)

    private val addWeekHighlightsUseCase
        get() = AddWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)

    private val deleteContestantsUseCase
        get() = DeleteContestantsUseCase(activityCompositionRoot.contestantsEndpoint)

    private val fetchContestantsUseCase
        get() = FetchContestantsUseCase(activityCompositionRoot.contestantsEndpoint)

    private val fetchWeekHighlightsUseCase
        get() = FetchWeekHighlightsUseCase(activityCompositionRoot.weekHighlightsEndpoint)

    private val updateContestantTimesSleptUseCase
        get() = UpdateContestantTimesSleptUseCase(activityCompositionRoot.contestantsEndpoint)

    private val updateContestantTimesDidNotSleptUseCase
        get() = UpdateContestantTimesDidNotSleptUseCase(activityCompositionRoot.contestantsEndpoint)

    private val updateContestantTimesFeltTiredUseCase
        get() = UpdateContestantTimesFeltTiredUseCase(activityCompositionRoot.contestantsEndpoint)

    val viewFactory
        get() = activityCompositionRoot.viewFactory

    val addContestantBottomSheetController
        get() = AddContestantBottomSheetController(
            messagesManager,
            activityCompositionRoot.coroutineScopeProvider.UIBoundScope,
            activityCompositionRoot.eventPublisher,
            addContestantUseCase
        )

    val contestantsTableController
        get() = ContestantsTableController(
            activityCompositionRoot.eventSubscriber,
            messagesManager,
            dialogManager,
            activityCompositionRoot.coroutineScopeProvider.UIBoundScope,
            fetchContestantsUseCase,
            deleteContestantsUseCase
        )

    val weekHighlightsController
        get() = WeekHighlightsController(
            messagesManager,
            activityCompositionRoot.eventSubscriber,
            dialogManager,
            activityCompositionRoot.coroutineScopeProvider.UIBoundScope,
            fetchWeekHighlightsUseCase
        )

    val addWeekHighlightsBottomSheetController
        get() = AddWeekHighlightsBottomSheetController(
            messagesManager,
            activityCompositionRoot.eventPublisher,
            activityCompositionRoot.coroutineScopeProvider.UIBoundScope,
            addWeekHighlightsUseCase
        )

    val contestantDetailsBottomSheetController
        get() = ContestantsDetailsBottomSheetController(
            messagesManager,
            activityCompositionRoot.eventPublisher,
            activityCompositionRoot.coroutineScopeProvider.UIBoundScope,
            updateContestantTimesSleptUseCase,
            updateContestantTimesDidNotSleptUseCase,
            updateContestantTimesFeltTiredUseCase
        )

    val promptDialogController
        get() = PromptDialogController(activityCompositionRoot.eventPublisher)
}
